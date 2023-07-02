package ru.example.quotes.model.dto;

import lombok.Data;
import ru.example.quotes.model.quotes.Exchange;

import java.time.LocalDate;

@Data
public class QuoteManualUpdate {

    private String instrumentId;
    private Exchange exchange;
    private LocalDate date;
}
