package ru.example.instruments.model.dto.bond;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BondStaticDto {

    @NotNull
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
    private BigDecimal macaulayDuration;
    private LocalDate settlementDate;
    private BigDecimal accruedInterest;
    private BigDecimal accruedInterestT0;
    private BigDecimal minimalLot;
    private BigDecimal assetSwapSpread;
    private BigDecimal marketCapitalization;
    private Long issuerId;
    private String securityName;
    private LocalDate finalMaturity;
    private BigDecimal zSpreadAsk;
    private BigDecimal outstandingAmount;
    private String industryGroup;
    private String dayCountConvention;
    private String shortDescription;
    private Boolean ruLombardList;
    private String issuerCountry;
    private String couponType;
    private BigDecimal durationAdjOasMid;
    private BigDecimal durationAdjMtyMid;
    private BigDecimal oasSpread;
    private BigDecimal durationSpread;
    private Boolean convertible;
    private BigDecimal minimalOrderIncrement;
    private String couponPrevFloat;
    private Boolean sinkable;
    private Boolean isPerpetual;
}
