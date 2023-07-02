package ru.bcs.perseus.bloomberg.out.ws.helper;

import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentType;
import ru.bcs.perseus.bloomberg.model.instrument.Rating;
import ru.bcs.perseus.bloomberg.model.instrument.equity.Equity;
import ru.bcs.perseus.bloomberg.model.instrument.equity.EquityStatic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.ID_ISIN;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.*;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.*;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.*;

public class EquityHelper {

    private EquityHelper() {
    }

    /**
     * @should set correct id
     * @should return correct result
     */
    public static Equity createEquity(Map<String, String> fieldValues) {
        Instrument instrument = InstrumentHelper.createInstrument(fieldValues, InstrumentType.EQUITY);
        if (instrument == null) {
            return null;
        }
        Rating rating = RatingHelper.getRating(fieldValues);
        EquityStatic equityStatic = getEquityStatic(fieldValues);

        return new Equity(instrument, equityStatic, rating);
    }

    private static EquityStatic getEquityStatic(Map<String, String> fieldValues) {

        String isin = getStringFieldValue(fieldValues, ID_ISIN);
        String name = getStringFieldValue(fieldValues, NAME);
        String currency = getStringFieldValue(fieldValues, CRNCY);
        String industrySector = getStringFieldValue(fieldValues, INDUSTRY_SECTOR);
        LocalDate settleDt = getLocalDateFieldValue(fieldValues, SETTLE_DT);

        BigDecimal marketCapitalization = getBigDecimalFieldValue(fieldValues, CUR_MKT_CAP);
        Long issuerId = getLongFieldValue(fieldValues, ID_BB_COMPANY);
        String securityName = getStringFieldValue(fieldValues, SECURITY_NAME);
        BigDecimal zSpreadAsk = getBigDecimalFieldValue(fieldValues, Z_SPRD_ASK);
        BigDecimal outstandingAmount = getBigDecimalFieldValue(fieldValues, AMT_OUTSTANDING);
        String industryGroup = getStringFieldValue(fieldValues, INDUSTRY_GROUP);
        String dayCountConvention = getStringFieldValue(fieldValues, DAY_CNT_DES);
        String shortDescription = getStringFieldValue(fieldValues, SECURITY_SHORT_DES);
        Boolean ruLombardList = getBooleanFromYNString(fieldValues, RUSSIAN_CB_LOMBARD);
        String issuerCountry = getStringFieldValue(fieldValues, COUNTRY);
        String dividendCurrency = getStringFieldValue(fieldValues, DVD_CRNCY);
        BigDecimal dividendShare12M = getBigDecimalFieldValue(fieldValues, DVD_SH_12M);
        LocalDate dividendExDate = getLocalDateFieldValue(fieldValues, DVD_EX_DT);
        LocalDate dividendRecordDate = getLocalDateFieldValue(fieldValues, DVD_RECORD_DT);
        BigDecimal dividendShareLast = getBigDecimalFieldValue(fieldValues, DVD_SH_LAST);
        BigDecimal dividendCashGrossNext = getBigDecimalFieldValue(fieldValues, EQY_DVD_CASH_GROSS_NEXT);
        LocalDate dividendCashRecordDateNext = getLocalDateFieldValue(fieldValues, EQY_DVD_CASH_RECORD_DT_NEXT);
        String dividendCurrencyNext = getStringFieldValue(fieldValues, EQY_DVD_CASH_CRNCY_NEXT);
        LocalDate dividendStockRecordDateNext = getLocalDateFieldValue(fieldValues, EQY_DVD_STK_RECORD_DT_NEXT);
        String dividendStockTypeNext = getStringFieldValue(fieldValues, EQY_DVD_STK_TYP_NEXT);
        String dividendStockAdjustFactor = getStringFieldValue(fieldValues, EQY_DVD_STK_ADJ_FCT_NEXT);
        LocalDate dividendSplitRecordDate = getLocalDateFieldValue(fieldValues, EQY_DVD_SPL_RECORD_DT_NEXT);
        String dividendSplitAdjustFactor = getStringFieldValue(fieldValues, EQY_DVD_SPL_ADJ_FCT_NEXT);
        Boolean dividendExFlag = getBooleanFromYNString(fieldValues, EQY_DVD_EX_FLAG);
        Boolean dividendStockFlag = getBooleanFromYNString(fieldValues, EQY_DVD_STK_EX_FLAG);
        Boolean stockSplitFlag = getBooleanFromYNString(fieldValues, EQY_DVD_SPL_EX_FLAG);


        return EquityStatic.builder()
                .isin(isin)
                .issuer(name)
                .currency(currency)
                .industrySector(industrySector)
                .settlementDate(settleDt)
                .marketCapitalization(marketCapitalization)
                .issuerId(issuerId)
                .securityName(securityName)
                .zSpreadAsk(zSpreadAsk)
                .outstandingAmount(outstandingAmount)
                .industryGroup(industryGroup)
                .dayCountConvention(dayCountConvention)
                .shortDescription(shortDescription)
                .ruLombardList(ruLombardList)
                .issuerCountry(issuerCountry)
                .dividendCurrency(dividendCurrency)
                .dividendShare12M(dividendShare12M)
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
                .dividendSplitAdjustFactor(dividendSplitAdjustFactor)
                .dividendExFlag(dividendExFlag)
                .dividendStockFlag(dividendStockFlag)
                .stockSplitFlag(stockSplitFlag)
                .build();
    }
}
