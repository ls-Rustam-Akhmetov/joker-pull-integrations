package ru.bcs.perseus.quotes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bcs.perseus.quotes.model.quotes.Exchange;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleInstrument {
    private String id;
    private String isin;
    private Exchange exchange;
    private String currency;
    private InstrumentType type;
}
