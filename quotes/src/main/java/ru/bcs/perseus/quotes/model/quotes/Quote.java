package ru.bcs.perseus.quotes.model.quotes;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import ru.bcs.perseus.quotes.model.InstrumentType;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

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

    @JsonIgnore
    public Quote merge(Quote updateQuote) {
        return updateQuote.toBuilder()
                .id(this.getId())
                .instrumentId(this.getInstrumentId())
                .exchange(this.getExchange())
                .type(firstNonNull(updateQuote.getType(), this.getType()))
                .time(firstNonNull(updateQuote.getTime(), this.getTime()))
                .bid(firstNonNull(updateQuote.getBid(), this.getBid()))
                .ask(firstNonNull(updateQuote.getAsk(), this.getAsk()))
                .last(firstNonNull(updateQuote.getLast(), this.getLast()))
                .close(firstNonNull(updateQuote.getClose(), this.getClose()))
                .open(firstNonNull(updateQuote.getOpen(), this.getOpen()))
                .max(firstNonNull(updateQuote.getMax(), this.getMax()))
                .min(firstNonNull(updateQuote.getMin(), this.getMin()))
                .strike(firstNonNull(updateQuote.getStrike(), this.getStrike()))
                .bidYTW(firstNonNull(updateQuote.getBidYTW(), this.getBidYTW()))
                .askYTW(firstNonNull(updateQuote.getAskYTW(), this.getAskYTW()))
                .ytw(firstNonNull(updateQuote.getYtw(), this.getYtw()))
                .ytwMidEod(firstNonNull(updateQuote.getYtwMidEod(), this.getYtwMidEod()))
                .vwap(firstNonNull(updateQuote.getVwap(), this.getVwap()))
                .midYtm(firstNonNull(updateQuote.getMidYtm(), this.getMidYtm()))
                .mid(firstNonNull(updateQuote.getMid(), this.getMid()))
                .priceChange1d(firstNonNull(updateQuote.getPriceChange1d(), this.getPriceChange1d()))
                .priceChangeWtd(firstNonNull(updateQuote.getPriceChangeWtd(), this.getPriceChangeWtd()))
                .priceChange1m(firstNonNull(updateQuote.getPriceChange1m(), this.getPriceChange1m()))
                .priceChange6m(firstNonNull(updateQuote.getPriceChange6m(), this.getPriceChange6m()))
                .priceChange1y(firstNonNull(updateQuote.getPriceChange1y(), this.getPriceChange1y()))
                .midYtw(firstNonNull(updateQuote.getMidYtw(), this.getMidYtw()))
                .couponPrevFloat(firstNonNull(updateQuote.getCouponPrevFloat(), this.getCouponPrevFloat()))
                .duration(firstNonNull(updateQuote.getDuration(), this.getDuration()))
                .coupon(firstNonNull(updateQuote.getCoupon(), this.getCoupon()))
                .couponFrequency(firstNonNull(updateQuote.getCouponFrequency(), this.getCouponFrequency()))
                .couponNextDate(firstNonNull(updateQuote.getCouponNextDate(), this.getCouponNextDate()))
                .dividendExDate(firstNonNull(updateQuote.getDividendExDate(), this.getDividendExDate()))
                .dividendRecordDate(firstNonNull(updateQuote.getDividendRecordDate(), this.getDividendRecordDate()))
                .dividendShareLast(firstNonNull(updateQuote.getDividendShareLast(), this.getDividendShareLast()))
                .dividendCashGrossNext(firstNonNull(updateQuote.getDividendCashGrossNext(), this.getDividendCashGrossNext()))
                .dividendCurrencyNext(firstNonNull(updateQuote.getDividendCurrencyNext(), this.getDividendCurrencyNext()))
                .dividendStockTypeNext(firstNonNull(updateQuote.getDividendStockTypeNext(), this.getDividendStockTypeNext()))
                .dividendStockAdjustFactor(firstNonNull(updateQuote.getDividendStockAdjustFactor(), this.getDividendStockAdjustFactor()))
                .dividendSplitAdjustFactor(firstNonNull(updateQuote.getDividendSplitAdjustFactor(), this.getDividendSplitAdjustFactor()))
                .dividendCashRecordDateNext(firstNonNull(updateQuote.getDividendCashRecordDateNext(), this.getDividendCashRecordDateNext()))
                .dividendStockRecordDateNext(firstNonNull(updateQuote.getDividendStockRecordDateNext(), this.getDividendStockRecordDateNext()))
                .dividendSplitRecordDate(firstNonNull(updateQuote.getDividendSplitRecordDate(), this.getDividendSplitRecordDate()))
                .dividendExFlag(firstNonNull(updateQuote.getDividendExFlag(), this.getDividendExFlag()))
                .dividendStockFlag(firstNonNull(updateQuote.getDividendStockFlag(), this.getDividendStockFlag()))
                .stockSplitFlag(firstNonNull(updateQuote.getStockSplitFlag(), this.getStockSplitFlag()))
                .accruedInterest(firstNonNull(updateQuote.getAccruedInterest(), this.getAccruedInterest()))
                .nominal(firstNonNull(updateQuote.getNominal(), this.getNominal()))
                .couponPrevDate(firstNonNull(updateQuote.getCouponPrevDate(), this.getCouponPrevDate()))
                .accruedInterestTradeDate(firstNonNull(updateQuote.getAccruedInterestTradeDate(), this.getAccruedInterestTradeDate()))
                .principalFactor(firstNonNull(updateQuote.getPrincipalFactor(), this.getPrincipalFactor()))
                .sinkableParMarket(firstNonNull(updateQuote.getSinkableParMarket(), this.getSinkableParMarket()))
                .defaulted(firstNonNull(updateQuote.getDefaulted(), this.getDefaulted()))
                .dividendPayDate(firstNonNull(updateQuote.getDividendPayDate(), this.getDividendPayDate()))
                .dividendDeclaredDate(firstNonNull(updateQuote.getDividendDeclaredDate(), this.getDividendDeclaredDate()))
                .build();
    }
}
