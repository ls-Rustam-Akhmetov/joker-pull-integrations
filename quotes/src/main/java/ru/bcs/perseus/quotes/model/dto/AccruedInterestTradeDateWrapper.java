package ru.bcs.perseus.quotes.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccruedInterestTradeDateWrapper extends QuoteManualUpdate {

    private BigDecimal value;
}
