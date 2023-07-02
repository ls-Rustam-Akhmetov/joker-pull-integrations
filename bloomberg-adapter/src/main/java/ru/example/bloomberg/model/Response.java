package ru.example.bloomberg.model;

import lombok.Builder;
import lombok.Data;
import ru.example.bloomberg.model.instrument.Dividend;
import ru.example.bloomberg.model.instrument.Instrument;
import ru.example.bloomberg.model.quote.Quote;

import java.util.List;

@Data
@Builder
public class Response {

    private final Status status;
    private final List<Instrument> instruments;
    private final List<Quote> quotes;
    private final List<Dividend> dividends;

    public enum Status {
        DATA_READY, DATA_PREPARATION_IN_PROGRESS, UNKNOWN
    }
}
