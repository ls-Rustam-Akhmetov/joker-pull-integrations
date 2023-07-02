package ru.bcs.perseus.bloomberg.out.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.bcs.perseus.bloomberg.model.dto.ActionDto;
import ru.bcs.perseus.bloomberg.model.dto.QuoteHistoryDownloadedDto;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.quote.Quote;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendAllInstruments(List<Instrument> instruments) {
        if (CollectionUtils.isEmpty(instruments)) {
            return;
        }

        instruments.stream()
                .filter(Objects::nonNull)
                .forEach(this::sendInstrument);
    }

    public void sendQuotes(List<Quote> quotes) {
        if (CollectionUtils.isEmpty(quotes)) {
            return;
        }

        quotes.stream()
                .filter(Objects::nonNull)
                .forEach(this::sendQuote);
    }

    public void sendAllCorporateActions(List<ActionDto> dividends) {
        if (CollectionUtils.isEmpty(dividends)) {
            return;
        }
        dividends.stream()
                .filter(Objects::nonNull)
                .forEach(this::sendCorporateAction);
    }

    private void sendQuote(Quote quote) {
        kafkaTemplate.send("quotes", quote.getInstrumentId(), quote);
        log.info("Quote was created: {}", quote.toString());
    }

    private void sendInstrument(Instrument instrument) {
        kafkaTemplate.send("instruments", instrument.getId(), instrument);
        log.info("Instrument was created: {}", instrument.toString());
    }

    private void sendCorporateAction(final ActionDto action) {
        kafkaTemplate.send("actions", action.getInstrumentId(), action);
        log.info("Action was created: {}", action.toString());
    }

    public void sendHistoryDownloadedEvent(String instrumentId) {
        boolean isValidDto = Objects.nonNull(instrumentId);
        if (!isValidDto) {
            return;
        }

        QuoteHistoryDownloadedDto dto = new QuoteHistoryDownloadedDto(instrumentId);
        kafkaTemplate.send("history_downloaded", instrumentId, instrumentId);
        log.info("Was send QuoteHistoryDownloadedEvent: {}", instrumentId);
    }
}

