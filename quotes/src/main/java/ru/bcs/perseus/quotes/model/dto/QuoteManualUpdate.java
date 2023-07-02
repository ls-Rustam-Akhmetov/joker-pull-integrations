package ru.bcs.perseus.quotes.model.dto;

import lombok.Data;
import ru.bcs.perseus.quotes.model.quotes.Exchange;

import java.time.LocalDate;

@Data
public class QuoteManualUpdate {

    private String instrumentId;
    private Exchange exchange;
    private LocalDate date;
}
