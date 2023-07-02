package ru.example.quotes.model.quotes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.example.quotes.model.InstrumentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "quotes")
@JsonIgnoreProperties(ignoreUnknown = true)
@CompoundIndex(name = "quote", def = "{'instrumentId':1, 'exchange':1, 'date':1}")
public class Quote {

    @Id
    private String id;

    @Indexed
    @NotNull(message = "instrumentId can not be null")
    private String instrumentId;

    @Indexed
    @NotNull(message = "exchange can not be null")
    private Exchange exchange;

    @NotNull(message = "type can not be null")
    private InstrumentType type;

    @Indexed
    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

    @NotNull
    private String source;
    private String isin;
    private String user;
    private String ticker;
    private String currency;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal last;
    private BigDecimal close;
    private BigDecimal open;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal strike;
    private BigDecimal bidYTW;
    private BigDecimal askYTW;
    private BigDecimal ytw;
    private BigDecimal vwap;

    private BigDecimal midYtm;
    private BigDecimal mid;
    private BigDecimal ytwMidEod;

    private BigDecimal priceChange1d;
    private BigDecimal priceChangeWtd;
    private BigDecimal priceChange1m;
    private BigDecimal priceChange6m;
    private BigDecimal priceChange1y;
    private BigDecimal midYtw;
    private String couponPrevFloat;
    private BigDecimal duration;
    private BigDecimal coupon;
    private BigDecimal couponFrequency;
    private LocalDate couponNextDate;
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
    private LocalDate dividendPayDate;
    private LocalDate dividendDeclaredDate;
    private Boolean dividendExFlag;
    private Boolean dividendStockFlag;
    private Boolean stockSplitFlag;
    private BigDecimal accruedInterest;
    private BigDecimal nominal;
    private LocalDate couponPrevDate;
    private BigDecimal accruedInterestTradeDate;
    private BigDecimal principalFactor;
    private String sinkableParMarket;
    private Boolean defaulted;
}
