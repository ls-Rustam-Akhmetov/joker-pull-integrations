package ru.example.instruments.out.ws.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import ru.example.instruments.model.*;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;
import static ru.example.instruments.model.Constant.InstrumentFields.*;
import static ru.example.instruments.model.Constant.QuoteFields.*;
import static ru.example.instruments.model.Constant.QuoteFields.FIGI;
import static ru.example.instruments.model.Constant.QuoteFields.ID_ISIN;
import static ru.example.instruments.model.Constant.QuoteFields.MARKET_SECTOR_DES;
import static ru.example.instruments.model.Constant.QuoteFields.TICKER;
import static ru.example.instruments.out.ws.helper.CalculationUtils.getAccruedInterest;


@Slf4j
public class QuoteParser {

    private static final MathContext MATH_CONTEXT = new MathContext(34, RoundingMode.HALF_UP);
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final String CROSS_RATE = "CROSS-RATE";

    private QuoteParser() {
    }

    public static Quote createQuote(Map<String, String> fieldValues) {

        String ticker = FieldParser.getStringFieldValue(fieldValues, TICKER);
        String marketSectorDes = FieldParser.getStringFieldValue(fieldValues, MARKET_SECTOR_DES);

        String isin = FieldParser.getStringFieldValue(fieldValues, ID_ISIN);
        BigDecimal close = getValue(fieldValues, PX_LAST_EOD);
        BigDecimal last = getValue(fieldValues, PX_LAST);
        BigDecimal min = getValue(fieldValues, PX_LOW);
        BigDecimal max = getValue(fieldValues, PX_HIGH);
        BigDecimal ytw = getPercentValue(fieldValues, YLD_CNV_LAST);
        BigDecimal ytwMidEod = getPercentValue(fieldValues, YLD_CNV_MID_EOD);
        BigDecimal askYTW = getPercentValue(fieldValues, YLD_CNV_ASK);
        BigDecimal bidYTW = getPercentValue(fieldValues, YLD_CNV_BID);
        BigDecimal vwap = getValue(fieldValues, VWAP_ALL_TRADES);
        String currency = FieldParser.getStringFieldValue(fieldValues, QUOTED_CRNCY);
        String figi = FieldParser.getStringFieldValue(fieldValues, FIGI);
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
        LocalDate couponNextDate = FieldParser.getLocalDateFieldValue(fieldValues, NXT_CPN_DT);
        BigDecimal couponFrequency = getValue(fieldValues, CPN_FREQ);

        BigDecimal modifiedDuration = FieldParser.getBigDecimalFieldValue(fieldValues, DUR_ADJ_MID);
        String couponPrevFloat = FieldParser.getStringFieldValue(fieldValues, PREV_FLT_CPN);
        LocalDate dividendExDate = FieldParser.getLocalDateFieldValue(fieldValues, DVD_EX_DT);
        LocalDate dividendRecordDate = FieldParser.getLocalDateFieldValue(fieldValues, DVD_RECORD_DT);
        BigDecimal dividendShareLast = FieldParser.getBigDecimalFieldValue(fieldValues, DVD_SH_LAST);
        BigDecimal dividendCashGrossNext = getValue(fieldValues, EQY_DVD_CASH_GROSS_NEXT);
        LocalDate dividendCashRecordDateNext = FieldParser.getLocalDateFieldValue(fieldValues, EQY_DVD_CASH_RECORD_DT_NEXT);
        String dividendCurrencyNext = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_CASH_CRNCY_NEXT);
        LocalDate dividendStockRecordDateNext = FieldParser.getLocalDateFieldValue(fieldValues, EQY_DVD_STK_RECORD_DT_NEXT);
        String dividendStockTypeNext = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_STK_TYP_NEXT);
        String dividendStockAdjustFactor = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_STK_ADJ_FCT_NEXT);
        LocalDate dividendSplitRecordDate = FieldParser.getLocalDateFieldValue(fieldValues, EQY_DVD_SPL_RECORD_DT_NEXT);
        LocalDate dividendPayDate = FieldParser.getLocalDateFieldValue(fieldValues, DVD_PAY_DT);
        LocalDate dividendDeclaredDate = FieldParser.getLocalDateFieldValue(fieldValues, DVD_DECLARED_DT);
        String dividendSplitAdjustFactor = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_SPL_ADJ_FCT_NEXT);
        Boolean dividendExFlag = FieldParser.getBooleanFromYNString(fieldValues, EQY_DVD_EX_FLAG);
        Boolean dividendStockFlag = FieldParser.getBooleanFromYNString(fieldValues, EQY_DVD_STK_EX_FLAG);
        Boolean stockSplitFlag = FieldParser.getBooleanFromYNString(fieldValues, EQY_DVD_SPL_EX_FLAG);
        BigDecimal intAcc = FieldParser.getBigDecimalFieldValue(fieldValues, INT_ACC);
        BigDecimal nominal = FieldParser.getBigDecimalFieldValue(fieldValues, PAR_AMT);
        BigDecimal accruedInterest = getAccruedInterest(nominal, intAcc);
        LocalDate couponPrevDate = FieldParser.getLocalDateFieldValue(fieldValues, PREV_CPN_DT);
        BigDecimal accTradeDate = FieldParser.getBigDecimalFieldValue(fieldValues, TRADE_DT_ACC_INT);
        BigDecimal accruedInterestTradeDate = getAccruedInterest(nominal, accTradeDate);
        BigDecimal principalFactor = FieldParser.getBigDecimalFieldValue(fieldValues, PRINCIPAL_FACTOR);
        String sinkableParMarket = FieldParser.getStringFieldValue(fieldValues, SINKABLE_PAR_MARKET);
        Boolean defaulted = FieldParser.getBooleanFromYNString(fieldValues, DEFAULTED);


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

        return switch (marketSector) {
            case EQUITY -> InstrumentType.EQUITY;
            case CURNCY -> InstrumentType.CURRENCY;
            case CORP, GOVT -> InstrumentType.BOND;
            default -> null;
        };
    }

    /**
     * @return if value = 0 return null
     */
    private static BigDecimal getValue(Map<String, String> fieldValues, String fieldName) {
        BigDecimal value = FieldParser.getBigDecimalFieldValue(fieldValues, fieldName);
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
