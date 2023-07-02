package ru.bcs.perseus.bloomberg.model.quote;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bcs.perseus.bloomberg.model.Exchange;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentType;

/**
 * Quote DTO for sending to quotes-storage
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Quote {

  private String instrumentId; // figi or currencyPair like USDRUB

  private String source;
  private LocalDate date;
  private LocalTime time;
  private String user;
  private InstrumentType type;
  private String ticker;
  private String isin; // ISIN OR currencyPair like USDRUB
  private String figi;
  @Setter
  private Exchange exchange;
  private String currency;

  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal open; //Цена открытия предыдущей торговой сессии
  private BigDecimal max;  //Максимальная цена предыдущей торговой сессии
  private BigDecimal min;  //Минимальная цена предыдущей торговой сессии
  private BigDecimal strike;
  private BigDecimal last; //Цена последней сделки
  private BigDecimal close;
  private BigDecimal bidYTW;
  private BigDecimal askYTW;
  private BigDecimal ytw;
  private BigDecimal vwap;
  private BigDecimal ytwMidEod;

  private BigDecimal midYtm;
  private BigDecimal mid;
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
