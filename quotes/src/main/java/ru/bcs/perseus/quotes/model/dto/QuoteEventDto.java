package ru.bcs.perseus.quotes.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bcs.perseus.quotes.model.InstrumentType;
import ru.bcs.perseus.quotes.model.quotes.Exchange;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class QuoteEventDto {
    private String instrumentId;
    private Exchange exchange;
    private LocalDateTime timestamp;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal last;
    private BigDecimal close;
    private InstrumentType type;
    private String ticker;
}
