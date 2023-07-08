package ru.example.instruments.out.ws.helper;


import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.Rating;
import ru.example.instruments.model.equity.Equity;
import ru.example.instruments.model.equity.EquityStatic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static ru.example.instruments.model.Constant.InstrumentFields.*;
import static ru.example.instruments.model.Constant.InstrumentFields.ID_ISIN;
import static ru.example.instruments.model.Constant.QuoteFields.*;


public class EquityParser {

    private EquityParser() {
    }

    public static Equity createEquity(Map<String, String> fieldValues) {
        Instrument instrument = InstrumentParser.createInstrument(fieldValues, InstrumentType.EQUITY);
        if (instrument == null) {
            return null;
        }
        Rating rating = RatingParser.getRating(fieldValues);
        EquityStatic equityStatic = getEquityStatic(fieldValues);

        return new Equity(instrument, equityStatic, rating);
    }

    private static EquityStatic getEquityStatic(Map<String, String> fieldValues) {

        String isin = FieldParser.getStringFieldValue(fieldValues, ID_ISIN);
        String name = FieldParser.getStringFieldValue(fieldValues, NAME);
        String currency = FieldParser.getStringFieldValue(fieldValues, CRNCY);
        String industrySector = FieldParser.getStringFieldValue(fieldValues, INDUSTRY_SECTOR);
        LocalDate settleDt = FieldParser.getLocalDateFieldValue(fieldValues, SETTLE_DT);

        BigDecimal marketCapitalization = FieldParser.getBigDecimalFieldValue(fieldValues, CUR_MKT_CAP);
        Long issuerId = FieldParser.getLongFieldValue(fieldValues, ID_BB_COMPANY);
        String securityName = FieldParser.getStringFieldValue(fieldValues, SECURITY_NAME);
        BigDecimal zSpreadAsk = FieldParser.getBigDecimalFieldValue(fieldValues, Z_SPRD_ASK);
        BigDecimal outstandingAmount = FieldParser.getBigDecimalFieldValue(fieldValues, AMT_OUTSTANDING);
        String industryGroup = FieldParser.getStringFieldValue(fieldValues, INDUSTRY_GROUP);
        String dayCountConvention = FieldParser.getStringFieldValue(fieldValues, DAY_CNT_DES);
        String shortDescription = FieldParser.getStringFieldValue(fieldValues, SECURITY_SHORT_DES);
        Boolean ruLombardList = FieldParser.getBooleanFromYNString(fieldValues, RUSSIAN_CB_LOMBARD);
        String issuerCountry = FieldParser.getStringFieldValue(fieldValues, COUNTRY);
        String dividendCurrency = FieldParser.getStringFieldValue(fieldValues, DVD_CRNCY);
        BigDecimal dividendShare12M = FieldParser.getBigDecimalFieldValue(fieldValues, DVD_SH_12M);
        LocalDate dividendExDate = FieldParser.getLocalDateFieldValue(fieldValues, DVD_EX_DT);
        LocalDate dividendRecordDate = FieldParser.getLocalDateFieldValue(fieldValues, DVD_RECORD_DT);
        BigDecimal dividendShareLast = FieldParser.getBigDecimalFieldValue(fieldValues, DVD_SH_LAST);
        BigDecimal dividendCashGrossNext = FieldParser.getBigDecimalFieldValue(fieldValues, EQY_DVD_CASH_GROSS_NEXT);
        LocalDate dividendCashRecordDateNext = FieldParser.getLocalDateFieldValue(fieldValues, EQY_DVD_CASH_RECORD_DT_NEXT);
        String dividendCurrencyNext = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_CASH_CRNCY_NEXT);
        LocalDate dividendStockRecordDateNext = FieldParser.getLocalDateFieldValue(fieldValues, EQY_DVD_STK_RECORD_DT_NEXT);
        String dividendStockTypeNext = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_STK_TYP_NEXT);
        String dividendStockAdjustFactor = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_STK_ADJ_FCT_NEXT);
        LocalDate dividendSplitRecordDate = FieldParser.getLocalDateFieldValue(fieldValues, EQY_DVD_SPL_RECORD_DT_NEXT);
        String dividendSplitAdjustFactor = FieldParser.getStringFieldValue(fieldValues, EQY_DVD_SPL_ADJ_FCT_NEXT);
        Boolean dividendExFlag = FieldParser.getBooleanFromYNString(fieldValues, EQY_DVD_EX_FLAG);
        Boolean dividendStockFlag = FieldParser.getBooleanFromYNString(fieldValues, EQY_DVD_STK_EX_FLAG);
        Boolean stockSplitFlag = FieldParser.getBooleanFromYNString(fieldValues, EQY_DVD_SPL_EX_FLAG);


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
