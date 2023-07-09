package ru.example.bloomberg.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.bloomberg.config.BloombergConfig;
import ru.example.bloomberg.mapper.ActionMapper;
import ru.example.bloomberg.model.Response;
import ru.example.bloomberg.model.db.RequestType;
import ru.example.bloomberg.model.db.Sync;
import ru.example.bloomberg.model.instrument.Dividend;
import ru.example.bloomberg.model.instrument.Instrument;
import ru.example.bloomberg.model.quote.Quote;
import ru.example.bloomberg.out.kafka.KafkaProducer;
import ru.example.bloomberg.out.ws.BloombergAdapterV2;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@AllArgsConstructor
public class BloombergServiceV2 {

    private final BloombergAdapterV2 bloombergAdapterV2;
    private final KafkaProducer kafkaProducer;

    // Example simplification
    public void getData(List<Sync> syncs, RequestType requestType) {
        CompletableFuture<Response> completableFuture = bloombergAdapterV2.getBloombergData(syncs, requestType);
        completableFuture.thenAccept(this::processData);
    }

    private void processData(Response response) {
        if (response.getStatus() != Response.Status.DATA_READY) return;

        List<Quote> quotes = response.getQuotes();
        List<Instrument> instruments = response.getInstruments();
        List<Dividend> dividends = response.getDividends();

        kafkaProducer.sendAllInstruments(instruments);
        kafkaProducer.sendQuotes(quotes);
        kafkaProducer.sendAllCorporateActions(ActionMapper.map(dividends));
    }
}
