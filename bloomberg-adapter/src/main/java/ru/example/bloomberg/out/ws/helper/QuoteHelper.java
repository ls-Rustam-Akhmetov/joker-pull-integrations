package ru.example.bloomberg.out.ws.helper;

import com.bloomberg.services.dlws.HistData;
import com.bloomberg.services.dlws.HistInstrumentData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import ru.example.bloomberg.model.Constant;
import ru.example.bloomberg.model.Exchange;
import ru.example.bloomberg.model.MarketSector;
import ru.example.bloomberg.model.db.Sync;
import ru.example.bloomberg.model.instrument.InstrumentType;
import ru.example.bloomberg.model.quote.Quote;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static ru.example.bloomberg.model.Constant.InstrumentFields.*;
import static ru.example.bloomberg.model.Constant.QuoteFields.FIGI;
import static ru.example.bloomberg.model.Constant.QuoteFields.ID_ISIN;
import static ru.example.bloomberg.model.Constant.QuoteFields.MARKET_SECTOR_DES;
import static ru.example.bloomberg.model.Constant.QuoteFields.TICKER;
import static ru.example.bloomberg.model.Constant.QuoteFields.*;
import static ru.example.bloomberg.util.BloombergUtils.getAccruedInterest;

@Slf4j
public class QuoteHelper {

    private static final MathContext MATH_CONTEXT = new MathContext(34, RoundingMode.HALF_UP);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final String CROSS_RATE = "CROSS-RATE";

    private QuoteHelper() {
    }

    public static Quote createQuote(Map<String, String> fieldValues) {

        String ticker = FieldHelper.getStringFieldValue(fieldValues, TICKER);
        String marketSectorDes = FieldHelper.getStringFieldValue(fieldValues, MARKET_SECTOR_DES);

        String isin = FieldHelper.getStringFieldValue(fieldValues, ID_ISIN);
        BigDecimal close = getValue(fieldValues, PX_LAST_EOD);
        BigDecimal last = getValue(fieldValues, PX_LAST);
        BigDecimal min = getValue(fieldValues, PX_LOW);
        BigDecimal max = getValue(fieldValues, PX_HIGH);
        BigDecimal ytw = getPercentValue(fieldValues, YLD_CNV_LAST);
        BigDecimal ytwMidEod = getPercentValue(fieldValues, YLD_CNV_MID_EOD);
        BigDecimal askYTW = getPercentValue(fieldValues, YLD_CNV_ASK);
        BigDecimal bidYTW = getPercentValue(fieldValues, YLD_CNV_BID);
        BigDecimal vwap = getValue(fieldValues, VWAP_ALL_TRADES);
        String currency = FieldHelper.getStringFieldValue(fieldValues, QUOTED_CRNCY);
        String figi = FieldHelper.getStringFieldValue(fieldValues, FIGI);
        BigDecimal bid = getValue(fieldValues, PX_BID);
        BigDecimal ask = getValue(fieldValues, PX_ASK);
        InstrumentType type = mapToInstrumentType(marketSectorDes);

        BigDecimal midYtm = getValue(fieldValues, YLD_YTM_MID);
        BigDecimal mid = getValue(fieldValues, PX_MID);
        BigDecimal priceChange1d = getValue(fieldValues, CHG_PCT_1D);
        BigDecimal priceChangeWtd = getValue(fieldValues, CHG_PCT_WTD);
        BigDecimal priceChange1m = getValue(fieldValues, CHG_PCT_1M);
        BigDecimal priceChange6m = getValue(fieldValues, CHG_PCT_6M);
        BigDecimal priceChange1y = getValue(fieldValues, CHG_PCT_1YR);
        BigDecimal midYtw = getValue(fieldValues, YLD_CNV_MID);

        BigDecimal coupon = getValue(fieldValues, CPN);
        LocalDate couponNextDate = FieldHelper.getLocalDateFieldValue(fieldValues, NXT_CPN_DT);
        BigDecimal couponFrequency = getValue(fieldValues, CPN_FREQ);

        BigDecimal modifiedDuration = FieldHelper.getBigDecimalFieldValue(fieldValues, DUR_ADJ_MID);
        String couponPrevFloat = FieldHelper.getStringFieldValue(fieldValues, PREV_FLT_CPN);
        LocalDate dividendExDate = FieldHelper.getLocalDateFieldValue(fieldValues, DVD_EX_DT);
        LocalDate dividendRecordDate = FieldHelper.getLocalDateFieldValue(fieldValues, DVD_RECORD_DT);
        BigDecimal dividendShareLast = FieldHelper.getBigDecimalFieldValue(fieldValues, DVD_SH_LAST);
        BigDecimal dividendCashGrossNext = getValue(fieldValues, EQY_DVD_CASH_GROSS_NEXT);
        LocalDate dividendCashRecordDateNext = FieldHelper.getLocalDateFieldValue(fieldValues, EQY_DVD_CASH_RECORD_DT_NEXT);
        String dividendCurrencyNext = FieldHelper.getStringFieldValue(fieldValues, EQY_DVD_CASH_CRNCY_NEXT);
        LocalDate dividendStockRecordDateNext = FieldHelper.getLocalDateFieldValue(fieldValues, EQY_DVD_STK_RECORD_DT_NEXT);
        String dividendStockTypeNext = FieldHelper.getStringFieldValue(fieldValues, EQY_DVD_STK_TYP_NEXT);
        String dividendStockAdjustFactor = FieldHelper.getStringFieldValue(fieldValues, EQY_DVD_STK_ADJ_FCT_NEXT);
        LocalDate dividendSplitRecordDate = FieldHelper.getLocalDateFieldValue(fieldValues, EQY_DVD_SPL_RECORD_DT_NEXT);
        LocalDate dividendPayDate = FieldHelper.getLocalDateFieldValue(fieldValues, DVD_PAY_DT);
        LocalDate dividendDeclaredDate = FieldHelper.getLocalDateFieldValue(fieldValues, DVD_DECLARED_DT);
        String dividendSplitAdjustFactor = FieldHelper.getStringFieldValue(fieldValues, EQY_DVD_SPL_ADJ_FCT_NEXT);
        Boolean dividendExFlag = FieldHelper.getBooleanFromYNString(fieldValues, EQY_DVD_EX_FLAG);
        Boolean dividendStockFlag = FieldHelper.getBooleanFromYNString(fieldValues, EQY_DVD_STK_EX_FLAG);
        Boolean stockSplitFlag = FieldHelper.getBooleanFromYNString(fieldValues, EQY_DVD_SPL_EX_FLAG);
        BigDecimal intAcc = FieldHelper.getBigDecimalFieldValue(fieldValues, INT_ACC);
        BigDecimal nominal = FieldHelper.getBigDecimalFieldValue(fieldValues, PAR_AMT);
        BigDecimal accruedInterest = getAccruedInterest(nominal, intAcc);
        LocalDate couponPrevDate = FieldHelper.getLocalDateFieldValue(fieldValues, PREV_CPN_DT);
        BigDecimal accTradeDate = FieldHelper.getBigDecimalFieldValue(fieldValues, TRADE_DT_ACC_INT);
        BigDecimal accruedInterestTradeDate = getAccruedInterest(nominal, accTradeDate);
        BigDecimal principalFactor = FieldHelper.getBigDecimalFieldValue(fieldValues, PRINCIPAL_FACTOR);
        String sinkableParMarket = FieldHelper.getStringFieldValue(fieldValues, SINKABLE_PAR_MARKET);
        Boolean defaulted = FieldHelper.getBooleanFromYNString(fieldValues, DEFAULTED);


        if (!allNotNull(bid, ask, type)) {
            log.warn("Invalid quote won't be processed, bid:{}, ask:{}, type:{} for isin:{}", bid, ask,
                    type, isin);
            return null;
        }

        boolean isCurrencyQuote = type == InstrumentType.CURRENCY;

        return Quote.builder()
                .source(Constant.SOURCE)
                .user(Constant.USERNAME)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .isin(isCurrencyQuote ? ticker : isin)
                .instrumentId(isCurrencyQuote ? ticker : figi)
                .close(close)
                .type(type)
                .last(last)
                .bid(bid)
                .ask(ask)
                .min(min)
                .max(max)
                .figi(figi)
                .ytw(ytw)
                .vwap(vwap)
                .bidYTW(bidYTW)
                .askYTW(askYTW)
                .ytwMidEod(ytwMidEod)
                .ticker(ticker)
                .exchange(isCurrencyQuote ? Exchange.OTC : null)
                .currency(isCurrencyQuote ? CROSS_RATE : currency)
                .midYtm(midYtm)
                .mid(mid)
                .priceChange1d(priceChange1d)
                .priceChangeWtd(priceChangeWtd)
                .priceChange1m(priceChange1m)
                .priceChange6m(priceChange6m)
                .priceChange1y(priceChange1y)
                .midYtw(midYtw)
                .coupon(coupon)
                .couponNextDate(couponNextDate)
                .couponFrequency(couponFrequency)
                .duration(modifiedDuration)
                .couponPrevFloat(couponPrevFloat)
                .dividendExDate(dividendExDate)
                .dividendRecordDate(dividendRecordDate)
                .dividendShareLast(dividendShareLast)
                .dividendCashGrossNext(dividendCashGrossNext)
                .dividendCashRecordDateNext(dividendCashRecordDateNext)
                .dividendCurrencyNext(dividendCurrencyNext)
                .dividendStockRecordDateNext(dividendStockRecordDateNext)
                .dividendStockTypeNext(dividendStockTypeNext)
                .dividendStockAdjustFactor(dividendStockAdjustFactor)
                .dividendSplitRecordDate(dividendSplitRecordDate)
                .dividendPayDate(dividendPayDate)
                .dividendDeclaredDate(dividendDeclaredDate)
                .dividendSplitAdjustFactor(dividendSplitAdjustFactor)
                .dividendExFlag(dividendExFlag)
                .dividendStockFlag(dividendStockFlag)
                .stockSplitFlag(stockSplitFlag)
                .nominal(nominal)
                .accruedInterest(accruedInterest)
                .couponPrevDate(couponPrevDate)
                .accruedInterestTradeDate(accruedInterestTradeDate)
                .principalFactor(principalFactor)
                .sinkableParMarket(sinkableParMarket)
                .defaulted(defaulted)
                .build();
    }

    public static Quote extractHistoryQuote(HistInstrumentData instrument, Sync sync) {
        List<HistData> histDataList = instrument.getData();

        BigDecimal ytwMidEod = getPercentValue(getHistoryValue(histDataList.get(0).getValue()));
        BigDecimal close = getHistoryValue(histDataList.get(1).getValue());

        XMLGregorianCalendar instrumentDate = instrument.getDate();
        LocalDate date = null;
        if (!isNull(instrumentDate)) {
            date = instrumentDate
                    .toGregorianCalendar()
                    .toZonedDateTime()
                    .toLocalDate();
        }

        if (isEmpty(ytwMidEod) && isEmpty(close) || isNull(date)) {
            log.warn("History quote won't be processed ytw and close is empty,"
                            + " ytwEod:{}, close:{}, figi:{}, isin:{}, exchange:{}",
                    ytwMidEod, close, sync.getFigi(), sync.getIsin(), sync.getExchange());
            return null;
        }

        return Quote.builder()
                .source(Constant.SOURCE)
                .user(Constant.USERNAME)
                .instrumentId(sync.getFigi())
                .figi(sync.getFigi())
                .type(sync.getInstrumentType())
                .exchange(sync.getExchange())
                .isin(sync.getIsin())
                .ytwMidEod(ytwMidEod)
                .close(close)
                .date(getNextWorkingDay(date))
                .currency(sync.getCurrency())
                .build();
    }

    private static LocalDate getNextWorkingDay(final LocalDate date) {
        final LocalDate nextDate = date.plusDays(1);
        final DayOfWeek weekDay = nextDate.getDayOfWeek();

        if (weekDay == SATURDAY) {
            return nextDate.plusDays(2);
        } else if (weekDay == SUNDAY) {
            return nextDate.plusDays(1);
        }
        return nextDate;
    }

    private static InstrumentType mapToInstrumentType(String marketSectorName) {

        if (StringUtils.isEmpty(marketSectorName)) {
            return null;
        }

        MarketSector marketSector = EnumUtils
                .getEnum(MarketSector.class, marketSectorName.toUpperCase());
        if (marketSector == null) {
            log.warn("Can't parse market sector des:{}", marketSectorName);
            return null;
        }

        switch (marketSector) {
            case EQUITY:
                return InstrumentType.EQUITY;
            case CURNCY:
                return InstrumentType.CURRENCY;
            case CORP:
                return InstrumentType.BOND;
            case GOVT:
                return InstrumentType.BOND;
            default:
                return null;
        }
    }

    private static BigDecimal getHistoryValue(String value) {
        BigDecimal bigDecimal = !FieldHelper.isEmptyValue(value) ? FieldHelper.getBigDecimalValue(value) : null;
        return isEmpty(bigDecimal) ? null : bigDecimal;
    }

    /**
     * @return if value = 0 return null
     */
    private static BigDecimal getValue(Map<String, String> fieldValues, String fieldName) {
        BigDecimal value = FieldHelper.getBigDecimalFieldValue(fieldValues, fieldName);
        if (isEmpty(value)) {
            return null;
        }
        return value;
    }

    private static BigDecimal getPercentValue(Map<String, String> fieldValues, String fieldName) {
        BigDecimal value = getValue(fieldValues, fieldName);
        return getPercentValue(value);
    }

    private static BigDecimal getPercentValue(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.divide(ONE_HUNDRED, MATH_CONTEXT);
    }

    private static boolean isEmpty(BigDecimal value) {
        return isNull(value) || value.signum() == 0;
    }
}
