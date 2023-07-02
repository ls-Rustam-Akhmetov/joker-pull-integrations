package ru.bcs.perseus.quotes.model.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccruedInterestTradeDateWrapper extends QuoteManualUpdate {

  private BigDecimal value;
}
