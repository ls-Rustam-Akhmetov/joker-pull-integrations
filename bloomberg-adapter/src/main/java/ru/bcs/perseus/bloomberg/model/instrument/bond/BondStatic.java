package ru.bcs.perseus.bloomberg.model.instrument.bond;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class BondStatic {

  private String isin;
  private String issuer;
  private String currency;
  private BigDecimal nominal;
  private LocalDate issueDate;
  private Long issuedAmount;
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
  private BigDecimal assetSwapSpread;
  private BigDecimal marketCapitalization;
  private Long issuerId;
  private String securityName;
  private LocalDate finalMaturity;
  private BigDecimal zSpreadAsk;
  private BigDecimal outstandingAmount;
  private BigDecimal macaulayDuration;
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
