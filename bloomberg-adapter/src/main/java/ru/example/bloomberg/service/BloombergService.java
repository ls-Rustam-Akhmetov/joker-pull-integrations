package ru.example.bloomberg.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.example.bloomberg.config.BloombergConfig;
import ru.example.bloomberg.mapper.ActionMapper;
import ru.example.bloomberg.model.Response;
import ru.example.bloomberg.model.db.RequestLog;
import ru.example.bloomberg.model.db.RequestLogStatus;
import ru.example.bloomberg.model.db.RequestType;
import ru.example.bloomberg.model.db.Sync;
import ru.example.bloomberg.model.instrument.Dividend;
import ru.example.bloomberg.model.instrument.Instrument;
import ru.example.bloomberg.model.quote.Quote;
import ru.example.bloomberg.out.kafka.KafkaProducer;
import ru.example.bloomberg.out.ws.BloombergAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class BloombergService {

    private final BloombergAdapter bloombergAdapter;
    private final RequestLogService requestLogService;
    private final KafkaProducer kafkaProducer;
    private final BloombergConfig bloombergConfig;
    private final SyncService syncService;

    public void requestForDataPreparation(List<Sync> syncs, RequestType requestType) {
        if (CollectionUtils.isEmpty(syncs)) {
            return;
        }
        String responseId = bloombergAdapter.requestForDataPreparation(syncs, requestType);
        RequestLog requestLog = RequestLog.builder()
                .responseId(responseId)
                .syncs(syncs)
                .requestDateTime(LocalDateTime.now())
                .requestType(requestType)
                .status(RequestLogStatus.PENDING)
                .build();
        requestLogService.save(requestLog);
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void tryToRetrieveData() {
        log.info("Trying to send retrieve data requests");

        List<RequestLog> requestsLogs = requestLogService.getPendingRequestsByLastTryTimeBefore(
                LocalDateTime.now().minusMinutes(7));

        log.info("Found {} pending requests to send", requestsLogs.size());

        requestsLogs.stream()
                .map(this::makeRequestToRetrieveData)
                .forEach(requestLogService::save);
    }

    private RequestLog makeRequestToRetrieveData(RequestLog requestLog) {
        String bloombergRequestId = requestLog.getResponseId();
        RequestLog resultResponseLog = requestLog.toBuilder().build();

        resultResponseLog.setLastTryDateTime(LocalDateTime.now());
        int triesNumber = requestLog.getTriesNumber();
        resultResponseLog.setTriesNumber(++triesNumber);

        Response response;
        Sync requestSync = requestLog.getSyncs().get(0);
        try {
            response = bloombergAdapter.requestForDataRetrieval(bloombergRequestId);
        } catch (Exception e) {
            log.error("Sync: {}, Error processed while try to retrieve data: {}",
                    requestSync,
                    e.toString());
            resultResponseLog.setStatus(RequestLogStatus.INTERNAL_ERROR);
            return resultResponseLog;
        }

        Response.Status responseStatusCode = response.getStatus();
        log.info("Response status code: {} for Request_id:{}", responseStatusCode, bloombergRequestId);
        if (responseStatusCode == Response.Status.DATA_READY) {
            dataReadyProcessing(requestLog, response);
            resultResponseLog.setStatus(RequestLogStatus.PROCESSING_FINISHED);
            return resultResponseLog;
        }

        if (responseStatusCode != Response.Status.DATA_PREPARATION_IN_PROGRESS) {
            resultResponseLog.setStatus(RequestLogStatus.BLOOMBERG_ERROR);
            return resultResponseLog;
        }
        if (triesNumber >= bloombergConfig.getMaxRetryLimit()) {
            resultResponseLog.setStatus(RequestLogStatus.TRIES_NUMBER_EXCEEDED);
            return resultResponseLog;
        }

        return resultResponseLog;
    }

    private void dataReadyProcessing(RequestLog requestLog, Response response) {
        List<Quote> quotes = response.getQuotes();
        List<Instrument> instruments = response.getInstruments();
        List<Dividend> dividends = response.getDividends();
        fillInstrumentsWithExchange(requestLog.getSyncs(), instruments, quotes);
        saveIsinToSynchronizations(requestLog.getSyncs(), instruments, quotes);

        kafkaProducer.sendAllInstruments(instruments);
        kafkaProducer.sendQuotes(quotes);
        kafkaProducer.sendAllCorporateActions(ActionMapper.map(dividends));
    }

    private void fillInstrumentsWithExchange(List<Sync> syncs, List<Instrument> instruments,
                                             List<Quote> quotes) {
        if (CollectionUtils.isEmpty(syncs) || (CollectionUtils.isEmpty(instruments) && CollectionUtils
                .isEmpty(quotes))) {
            return;
        }

        for (int i = 0; i < syncs.size(); i++) {
            Sync sync = syncs.get(i);
            Instrument instrument = instruments.get(i);
            Quote quote = quotes.get(i);

            if (instrument != null) {
                instrument.setExchange(sync.getExchange());
            }
            if (quote != null) {
                quote.setExchange(sync.getExchange());
            }
        }
    }

    private void saveIsinToSynchronizations(List<Sync> syncs, List<Instrument> instruments,
                                            List<Quote> quotes) {
        if (CollectionUtils.isEmpty(syncs) || CollectionUtils.isEmpty(quotes)) {
            return;
        }

        List<Sync> updatedSyncs = new ArrayList<>();

        for (int i = 0; i < syncs.size(); i++) {
            Sync sync = syncs.get(i);
            Instrument instrument = instruments.get(i);
            Quote quote = quotes.get(i);

            if (quote != null && sync.getFigi() == null) {
                String figi = quote.getFigi();
                sync.setFigi(figi);
                updatedSyncs.add(sync);
            }
            if (instrument != null && sync.getFigi() == null) {
                String figi = instrument.getId();
                sync.setFigi(figi);
                updatedSyncs.add(sync);
            }
        }
        List<Sync> correctSync = updatedSyncs.stream()
                .filter(sync -> sync.getIsin() != null).collect(Collectors.toList());
        syncService.save(correctSync);
    }
}
