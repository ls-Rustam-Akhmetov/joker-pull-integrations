package ru.example.quotes.out.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.example.quotes.model.quotes.Quote;

import static ru.example.quotes.config.KafkaConfig.QUOTES_TOPIC_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteKafkaProducer {

    private final KafkaTemplate<String, Quote> kafkaTemplate;

    public void sendQuote(Quote quote) {
        kafkaTemplate.send(QUOTES_TOPIC_NAME, quote.getInstrumentId(), quote);
        log.info("Quote was send: {}", quote);
    }
}

