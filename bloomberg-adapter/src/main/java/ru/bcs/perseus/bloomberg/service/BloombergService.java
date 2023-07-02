package ru.bcs.perseus.bloomberg.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.bcs.perseus.bloomberg.config.BloombergConfig;
import ru.bcs.perseus.bloomberg.mapper.ActionMapper;
import ru.bcs.perseus.bloomberg.model.Response;
import ru.bcs.perseus.bloomberg.model.db.*;
import ru.bcs.perseus.bloomberg.model.instrument.Dividend;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.quote.Quote;
import ru.bcs.perseus.bloomberg.out.kafka.KafkaProducer;
import ru.bcs.perseus.bloomberg.out.ws.BloombergRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static ru.bcs.perseus.bloomberg.model.db.RequestType.COMBINED;
import static ru.bcs.perseus.bloomberg.model.db.RequestType.QUOTES_HISTORY;


@Slf4j
@Service
@AllArgsConstructor
public class BloombergService {

    private final BloombergRepository bloombergRepository;
    private final RequestLogService requestLogService;
    private final KafkaProducer kafkaProducer;
    private final BloombergConfig bloombergConfig;
    private final SyncService syncService;
    private final ExecutorService executorService;
    private final HistoryQuoteDownloadStatusService historyQuoteDownloadStatusService;

    public void requestForDataPreparation(List<Sync> syncs, RequestType requestType) {
        if (CollectionUtils.isEmpty(syncs)) {
            return;
        }
        log.info("Try make bloomberg request for data preparation with ids: {} and requestType:{}",
                syncs, requestType.toString());

        String responseId = bloombergRepository.requestForDataPreparation(syncs, requestType);

        log.info("The request was successful, returned request_id: {}", responseId);

        RequestLog requestLog = RequestLog.builder()
                .responseId(responseId)
                .syncs(syncs)
                .requestDateTime(LocalDateTime.now())
                .requestType(requestType)
                .status(RequestLogStatus.PENDING)
                .build();
        requestLogService.save(requestLog);
    }

    public void requestHistoryQuotesPreparation(Sync sync) {
        String instrumentId = sync.getFigi();
        if (StringUtils.isEmpty(instrumentId)) {
            log.warn("No instrumentId for quotes history request[{}]", sync);
            return;
        }

        Optional<HistoryQuotesDownloadStatus> optionalHistoryRecord =
                historyQuoteDownloadStatusService.findById(instrumentId);
        boolean isHistoryQuoteRequestAlreadyWasMade = optionalHistoryRecord.isPresent();

        if (isHistoryQuoteRequestAlreadyWasMade) {
            HistoryQuotesDownloadStatus historyRecord = optionalHistoryRecord.get();
            log.warn("Abort history quote request, it was already made in the past: {}", historyRecord);
            return;
        }

        log.info("Retrieve history quotes for {}", sync);
        int historyPeriodInDays = bloombergConfig.getHistoryPeriodInDays();
        String responseId = bloombergRepository
                .requestInstrumentsQuotesHistory(sync, historyPeriodInDays);

        RequestLog requestLog = RequestLog.builder()
                .responseId(responseId)
                .syncs(singletonList(sync))
                .requestDateTime(LocalDateTime.now())
                .requestType(QUOTES_HISTORY)
                .status(RequestLogStatus.PENDING)
                .build();
        requestLogService.save(requestLog);
        historyQuoteDownloadStatusService.insert(instrumentId);
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

    RequestLog makeRequestToRetrieveData(RequestLog requestLog) {
        String bloombergRequestId = requestLog.getResponseId();
        RequestLog resultResponseLog = requestLog.toBuilder().build();

        resultResponseLog.setLastTryDateTime(LocalDateTime.now());
        int triesNumber = requestLog.getTriesNumber();
        resultResponseLog.setTriesNumber(++triesNumber);

        Response response;
        Sync requestSync = requestLog.getSyncs().get(0);
        try {
            if (QUOTES_HISTORY == requestLog.getRequestType()) {
                response = bloombergRepository.
                        requestForQuotesHistoryDataRetrieval(bloombergRequestId, requestSync);
            } else {
                response = bloombergRepository.requestForDataRetrieval(bloombergRequestId);
            }
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

    private void requestHistoryQuotesPreparation(Instrument newInstrument) {
        Sync sync = new Sync(newInstrument.getIsin(), newInstrument.getExchange());
        sync.setFigi(newInstrument.getId());
        sync.setCurrency(newInstrument.getCurrency());
        sync.setInstrumentType(newInstrument.getType());
        requestHistoryQuotesPreparation(sync);
    }

    private void dataReadyProcessing(RequestLog requestLog, Response response) {
        List<Quote> quotes = response.getQuotes();
        if (QUOTES_HISTORY == requestLog.getRequestType()) {
            log.info("Response for history quotes: {}", quotes);
            finishHistoryQuoteProcessing(quotes);
        } else {
            List<Instrument> instruments = response.getInstruments();
            final List<Dividend> dividends = response.getDividends();
            fillInstrumentsWithExchange(requestLog.getSyncs(), instruments, quotes);
            saveFigiToSynchronizations(requestLog.getSyncs(), instruments, quotes);

            kafkaProducer.sendAllInstruments(instruments);
            kafkaProducer.sendQuotes(quotes);
            kafkaProducer.sendAllCorporateActions(ActionMapper.map(dividends));

            if (COMBINED == requestLog.getRequestType()) {
                Instrument newInstrument = instruments.get(0);
                executorService.execute(() -> requestHistoryQuotesPreparation(newInstrument));
            }
        }
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

    private void saveFigiToSynchronizations(List<Sync> syncs, List<Instrument> instruments,
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

    private void finishHistoryQuoteProcessing(List<Quote> quotes) {
        if (CollectionUtils.isEmpty(quotes)) {
            return;
        }

        String instrumentId = quotes.get(0).getInstrumentId();
        kafkaProducer.sendQuotes(quotes);
        historyQuoteDownloadStatusService.saveDownloadTime(instrumentId, LocalDateTime.now());
        kafkaProducer.sendHistoryDownloadedEvent(instrumentId);
    }
}
