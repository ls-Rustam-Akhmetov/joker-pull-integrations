package ru.bcs.perseus.bloomberg.model;

import lombok.Builder;
import lombok.Data;
import ru.bcs.perseus.bloomberg.model.instrument.Dividend;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.quote.Quote;

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
