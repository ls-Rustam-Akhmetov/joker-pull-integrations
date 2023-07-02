package ru.bcs.perseus.quotes.model.dto.instruments.bond;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BondStaticDTO {

    private String isin;
    private String issuer;
    private String riskCountry;
    private String currency;
    private BigDecimal nominal;
    private LocalDate issueDate;
    private BigDecimal issuedAmount;
    private String paymentRank;
    private String industrySector;
    private Boolean putable;
    private Boolean callable;
    private LocalDate nextPutDate;
    private LocalDate nextCallDate;
    private BigDecimal coupon;
    private LocalDate couponNextDate;
    private LocalDate couponPrevDate;
    private BigDecimal couponFrequency;
    private LocalDate maturity;
    private BigDecimal duration;
    private LocalDate settlementDate;
    private BigDecimal accruedInterest;
    private BigDecimal accruedInterestT0;
    private BigDecimal minimalLot;
    private Boolean convertible;
}
