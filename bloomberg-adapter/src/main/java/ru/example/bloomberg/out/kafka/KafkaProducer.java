package ru.example.bloomberg.out.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.example.bloomberg.model.dto.ActionDto;
import ru.example.bloomberg.model.instrument.Instrument;
import ru.example.bloomberg.model.quote.Quote;

import java.util.List;
import java.util.Objects;

import static ru.example.bloomberg.config.KafkaConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendAllInstruments(List<Instrument> instruments) {
        instruments.stream()
                .filter(Objects::nonNull)
                .forEach(this::sendInstrument);
    }

    public void sendQuotes(List<Quote> quotes) {
        quotes.stream()
                .filter(Objects::nonNull)
                .forEach(this::sendQuote);
    }

    public void sendAllCorporateActions(List<ActionDto> dividends) {
        dividends.stream()
                .filter(Objects::nonNull)
                .forEach(this::sendCorporateAction);
    }

    private void sendQuote(Quote quote) {
        kafkaTemplate.send(QUOTES_TOPIC_NAME, quote.getInstrumentId(), quote);
        log.info("Quote was created: {}", quote);
    }

    private void sendInstrument(Instrument instrument) {
        kafkaTemplate.send(INSTRUMENTS_TOPIC_NAME, instrument.getId(), instrument);
        log.info("Instrument was created: {}", instrument);
    }

    private void sendCorporateAction(final ActionDto action) {
        kafkaTemplate.send(ACTIONS_TOPIC_NAME, action.getInstrumentId(), action);
        log.info("Action was created: {}", action);
    }

    public void sendHistoryDownloadedEvent(String instrumentId) {
        boolean isValidDto = Objects.nonNull(instrumentId);
        if (!isValidDto) {
            return;
        }

        kafkaTemplate.send(HISTORY_TOPIC_NAME, instrumentId, instrumentId);
        log.info("Was send QuoteHistoryDownloadedEvent: {}", instrumentId);
    }
}

