package ru.example.quotes.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class NominalWrapper extends QuoteManualUpdate {

    private BigDecimal value;
}
