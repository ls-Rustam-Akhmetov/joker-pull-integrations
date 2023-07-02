package ru.example.quotes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.example.quotes.model.InstrumentType;
import ru.example.quotes.model.quotes.Exchange;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {

    private String instrumentId;
    private Exchange exchange;

    private String isin;
    private LocalDate date;
    private LocalTime time;
    private InstrumentType type;
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
