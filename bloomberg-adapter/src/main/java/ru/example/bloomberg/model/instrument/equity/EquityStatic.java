package ru.example.bloomberg.model.instrument.equity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class EquityStatic {

    private String isin;
    private String issuer;
    private String currency;
    private String industrySector;
    private LocalDate settlementDate;
    private BigDecimal marketCapitalization;
    private Long issuerId;
    private String securityName;
    private BigDecimal zSpreadAsk;
    private BigDecimal outstandingAmount;
    private String industryGroup;
    private String dayCountConvention;
    private String shortDescription;
    private Boolean ruLombardList;
    private String issuerCountry;
    private String dividendCurrency;
    private BigDecimal dividendShare12M;
    private LocalDate dividendExDate;
    private LocalDate dividendRecordDate;
    private BigDecimal dividendShareLast;
    private BigDecimal dividendCashGrossNext;
    private String dividendCurrencyNext;
    private String dividendStockTypeNext;
    private String dividendStockAdjustFactor;
    private String dividendSplitAdjustFactor;
    private LocalDate dividendCashRecordDateNext;
    private LocalDate dividendStockRecordDateNext;
    private LocalDate dividendSplitRecordDate;
    private Boolean dividendExFlag;
    private Boolean dividendStockFlag;
    private Boolean stockSplitFlag;
}
