package ru.bcs.perseus.quotes.model.dto.instruments.equity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EquityStaticDTO {

    private String isin;
    private String issuer;
    private String riskCountry;
    private String currency;
    private String industrySector;
    private LocalDate settlementDate;
    private BigDecimal minimalLot;

    private String dividendCurrency;
    private BigDecimal dividendShare12M;
}
