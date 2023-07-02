package ru.bcs.perseus.quotes.model.dto;

import java.time.LocalDate;
import lombok.Data;
import ru.bcs.perseus.quotes.model.quotes.Exchange;

@Data
public class QuoteManualUpdate {

  private String instrumentId;
  private Exchange exchange;
  private LocalDate date;
}
