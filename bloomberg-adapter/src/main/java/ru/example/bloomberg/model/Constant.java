package ru.example.bloomberg.model;

/**
 * Constants class
 */
public abstract class Constant {

    public static final String SOURCE = "BLOOMBERG-ADAPTER";
    public static final String USERNAME = "SYSTEM";

    private Constant() {
    }

    public interface InstrumentFields {

        String MARKET_SECTOR_DES = "MARKET_SECTOR_DES";
        String SECURITY_DES = "SECURITY_DES";
        String TICKER = "TICKER";
        String ID_ISIN = "ID_ISIN";
        String NAME = "NAME";
        String CRNCY = "CRNCY";
        String PAR_AMT = "PAR_AMT";
        String ANNOUNCE_DT = "ANNOUNCE_DT";
        String AMT_ISSUED = "AMT_ISSUED";
        String PAYMENT_RANK = "PAYMENT_RANK";
        String INDUSTRY_SECTOR = "INDUSTRY_SECTOR";
        String PUTABLE = "PUTABLE";
        String CALLABLE = "CALLABLE";
        String CPN_FREQ = "CPN_FREQ";
        String MATURITY = "MATURITY";

        String NXT_PUT_DT = "NXT_PUT_DT";
        String NXT_CALL_DT = "NXT_CALL_DT";
        String CPN = "CPN";
        String NXT_CPN_DT = "NXT_CPN_DT";
        String PREV_CPN_DT = "PREV_CPN_DT";
        String DUR_MID = "DUR_MID";
        String SETTLE_DT = "SETTLE_DT";
        String INT_ACC = "INT_ACC";
        String RTG_MOODY_NO_WATCH = "RTG_MOODY_NO_WATCH";
        String RTG_SP_NO_WATCH = "RTG_SP_NO_WATCH";
        String RTG_FITCH_NO_WATCH = "RTG_FITCH_NO_WATCH";
        String BB_COMPOSITE = "BB_COMPOSITE";
        String MIN_PIECE = "MIN_PIECE";

        String FIGI = "ID_BB_GLOBAL";
        String COMPOSITE_FIGI = "COMPOSITE_ID_BB_GLOBAL";
        String EXCHANGE = "EXCH_CODE";

        String MARKET_SECTOR = "MARKET_SECTOR";
        String YAS_ASW_SPREAD = "YAS_ASW_SPREAD";

        String CUR_MKT_CAP = "CUR_MKT_CAP";
        String ID_BB_COMPANY = "ID_BB_COMPANY";
        String SECURITY_NAME = "SECURITY_NAME";
        String FINAL_MATURITY = "FINAL_MATURITY";
        String Z_SPRD_ASK = "Z_SPRD_ASK";
        String AMT_OUTSTANDING = "AMT_OUTSTANDING";
        String DUR_ADJ_MID = "DUR_ADJ_MID";
        String INDUSTRY_GROUP = "INDUSTRY_GROUP";
        String DAY_CNT_DES = "DAY_CNT_DES";
        String SECURITY_SHORT_DES = "SECURITY_SHORT_DES";
        String RUSSIAN_CB_LOMBARD = "RUSSIAN_CB_LOMBARD";
        String COUNTRY = "COUNTRY";
        String CPN_TYP = "CPN_TYP";
        String DUR_ADJ_OAS_MID = "DUR_ADJ_OAS_MID";
        String DUR_ADJ_MTY_MID = "DUR_ADJ_MTY_MID";
        String OAS_SPREAD_DUR_MID = "OAS_SPREAD_DUR_MID";
        String SPREAD_DUR_ADJ_ASK = "SPREAD_DUR_ADJ_ASK";

        String DVD_CRNCY = "DVD_CRNCY";
        String DVD_SH_12M = "DVD_SH_12M";

        String CFI_CODE = "CFI_CODE";
        String COUNTRY_ISO = "COUNTRY_ISO";
        String CNTRY_OF_DOMICILE = "CNTRY_OF_DOMICILE";
        String BLOOMBERG_CFI_CODE = "BLOOMBERG_CFI_CODE";
        String PX_TRADE_LOT_SIZE = "PX_TRADE_LOT_SIZE";
        String PX_QUOTE_LOT_SIZE = "PX_QUOTE_LOT_SIZE";
        String PX_ROUND_LOT_SIZE = "PX_ROUND_LOT_SIZE";
        String CONVERTIBLE = "CONVERTIBLE";
        String QUOTE_FACTOR = "QUOTE_FACTOR";
        String MIN_INCREMENT = "MIN_INCREMENT";
        String TRADE_DT_ACC_INT = "TRADE_DT_ACC_INT";
        String PRINCIPAL_FACTOR = "PRINCIPAL_FACTOR";
        String SINKABLE_PAR_MARKET = "SINKABLE_PAR_MARKET";
        String SINKABLE = "SINKABLE";
        String DEFAULTED = "DEFAULTED";
        String IS_PERPETUAL = "IS_PERPETUAL";
    }

    public interface QuoteFields {

        String MARKET_SECTOR_DES = "MARKET_SECTOR_DES";
        String TICKER = "TICKER";
        String ID_ISIN = "ID_ISIN";
        String FIGI = "ID_BB_GLOBAL";
        String PX_LAST_EOD = "PX_LAST_EOD";
        String PX_LAST = "PX_LAST";
        String PX_BID = "PX_BID";
        String PX_ASK = "PX_ASK";
        String PX_LOW = "PX_LOW";
        String PX_HIGH = "PX_HIGH";
        String QUOTED_CRNCY = "QUOTED_CRNCY";
        String YLD_CNV_BID = "YLD_CNV_BID";
        String YLD_CNV_ASK = "YLD_CNV_ASK";
        String YLD_CNV_LAST = "YLD_CNV_LAST";
        String YLD_CNV_MID_EOD = "YIELD_TO_CONVENTION_MID_EOD";

        String VWAP_ALL_TRADES = "VWAP_ALL_TRADES";

        String YLD_YTM_MID = "YLD_YTM_MID";
        String PX_MID = "PX_MID";
        String CHG_PCT_1D = "CHG_PCT_1D";
        String CHG_PCT_WTD = "CHG_PCT_WTD";
        String CHG_PCT_1M = "CHG_PCT_1M";
        String CHG_PCT_6M = "CHG_PCT_6M";
        String CHG_PCT_1YR = "CHG_PCT_1YR";
        String YLD_CNV_MID = "YLD_CNV_MID";

        String PREV_FLT_CPN = "PREV_FLT_CPN";
        String DVD_EX_DT = "DVD_EX_DT";
        String DVD_RECORD_DT = "DVD_RECORD_DT";
        String DVD_SH_LAST = "DVD_SH_LAST";
        String EQY_DVD_CASH_GROSS_NEXT = "EQY_DVD_CASH_GROSS_NEXT";
        String EQY_DVD_CASH_RECORD_DT_NEXT = "EQY_DVD_CASH_RECORD_DT_NEXT";
        String EQY_DVD_CASH_CRNCY_NEXT = "EQY_DVD_CASH_CRNCY_NEXT";
        String EQY_DVD_STK_RECORD_DT_NEXT = "EQY_DVD_STK_RECORD_DT_NEXT";
        String EQY_DVD_STK_TYP_NEXT = "EQY_DVD_STK_TYP_NEXT";
        String EQY_DVD_STK_ADJ_FCT_NEXT = "EQY_DVD_STK_ADJ_FCT_NEXT";
        String EQY_DVD_SPL_RECORD_DT_NEXT = "EQY_DVD_SPL_RECORD_DT_NEXT";
        String EQY_DVD_SPL_ADJ_FCT_NEXT = "EQY_DVD_SPL_ADJ_FCT_NEXT";
        String DVD_PAY_DT = "DVD_PAY_DT";
        String DVD_DECLARED_DT = "DVD_DECLARED_DT";
        String EQY_DVD_EX_FLAG = "EQY_DVD_EX_FLAG";
        String EQY_DVD_STK_EX_FLAG = "EQY_DVD_STK_EX_FLAG";
        String EQY_DVD_SPL_EX_FLAG = "EQY_DVD_SPL_EX_FLAG";
    }

    public interface InstrumentFieldsReadable {

        String ISIN_FIELD = "isin";
        String MATURITY_FIELD = "maturity";
        String TICKER = "ticker";
        String COMPOSITE_FIGI_FIELD = "compositeFigi";
        String BL_EXCHANGE_FIELD = "blExchange";
        String MARKET_SECTOR_FIELD = "marketSector";
        String MARKET_SECTOR_ID_FIELD = "marketSectorId";
        String CURRENCY_FIELD = "currency";
        String CFI_CODE_FIELD = "cfiCode";
        String BLOOMBERG_CFI_CODE_FIELD = "bloombergCfiCode";
        String COUNTRY_ISO_FIELD = "countryIso";
        String COUNTRY_DOMICILE_FIELD = "countryDomicile";
        String TRADE_LOT_SIZE_FIELD = "tradeLotSize";
        String QUOTE_LOT_SIZE_FIELD = "quoteLotSize";
        String ROUND_LOT_SIZE_FIELD = "roundLotSize";
        String ISSUER_FIELD = "issuer";
        String NOMINAL_FIELD = "nominal";
        String ISSUE_DATE_FIELD = "issueDate";
        String ISSUED_AMOUNT_FIELD = "issuedAmount";
        String PAYMENT_RANK_FIELD = "paymentRank";
        String INDUSTRY_SECTOR_FIELD = "industrySector";
        String PUTABLE_FIELD = "putable";
        String CALLABLE_FIELD = "callable";
        String NEXT_PUT_DATE_FIELD = "nextPutDate";
        String NEXT_CALL_DATE_FIELD = "nextCallDate";
        String COUPON_FIELD = "coupon";
        String COUPON_NEXT_DATE_FIELD = "couponNextDate";
        String COUPON_PREV_DATE_FIELD = "couponPrevDate";
        String COUPON_FREQUENCY_FIELD = "couponFrequency";
        String DURATION_FIELD = "duration";
        String SETTLEMENT_DATE_FIELD = "settlementDate";
        String ACCRUED_INTEREST_FIELD = "accruedInterest";
        String MINIMAL_LOT_FIELD = "minimalLot";
        String ASSET_SWAP_SPREAD_FIELD = "assetSwapSpread";
        String MARKET_CAPITALIZATION_FIELD = "marketCapitalization";
        String ISSUER_ID_FIELD = "issuerId";
        String SECURITY_NAME_FIELD = "securityName";
        String FINAL_MATURITY_FIELD = "finalMaturity";
        String Z_SPREAD_ASK_FIELD = "zSpreadAsk";
        String OUTSTANDING_AMOUNT_FIELD = "outstandingAmount";
        String MACAULAY_DURATION_FIELD = "macaulayDuration";
        String INDUSTRY_GROUP_FIELD = "industryGroup";
        String DAY_COUNT_CONVENTION_FIELD = "dayCountConvention";
        String SHORT_DESCRIPTION_FIELD = "shortDescription";
        String RU_LOMBARD_LIST_FIELD = "ruLombardList";
        String ISSUER_COUNTRY_FIELD = "issuerCountry";
        String COUPON_TYPE_FIELD = "couponType";
        String DURATION_ADJ_OAS_MID_FIELD = "durationAdjOasMid";
        String DURATION_ADJ_MTY_MID_FIELD = "durationAdjMtyMid";
        String OAS_SPREAD_FIELD = "oasSpread";
        String DURATION_SPREAD_FIELD = "durationSpread";
        String CONVERTIBLE_FIELD = "convertible";
        String DIVIDEND_CURRENCY_FIELD = "dividendCurrency";
        String DIVIDEND_SHARE_12M_FIELD = "dividendShare12M";
        String SOURCE_FIELD = "source";
        String USER_FIELD = "user";
        String TYPE_FIELD = "type";
        String CURRENCY_QUOTE_FACTOR_FIELD = "currencyQuoteFactor";
        String MINIMAL_ORDER_INCREMENT_FIELD = "minimalOrderIncrement";
        String EXCHANGE_FIELD = "exchange";
        String DIVIDEND_EX_DATE = "dividendExDate";
        String DIVIDEND_RECORD_DATE = "dividendRecordDate";
        String DIVIDEND_SHARE_LIST = "dividendShareLast";
        String DIVIDEND_CASH_GROSS_NEXT = "dividendCashGrossNext";
        String DIVIDEND_CURRENCY_NEXT = "dividendCurrencyNext";
        String DIVIDEND_STOCK_TYPE_NEXT = "dividendStockTypeNext";
        String DIVIDEND_STOCK_ADJUST_FACTOR = "dividendStockAdjustFactor";
        String DIVIDEND_SPLIT_ADJUST_FACTOR = "dividendSplitAdjustFactor";
        String DIVIDEND_CASH_RECORD_DATE_NEXT = "dividendCashRecordDateNext";
        String DIVIDEND_STOCK_RECORD_DATE_NEXT = "dividendStockRecordDateNext";
        String DIVIDEND_SPLIT_RECORD_DATE_NEXT = "dividendSplitRecordDate";
        String DIVIDEND_EX_FLAG = "dividendExFlag";
        String DIVIDEND_STOCK_FLAG = "dividendStockFlag";
        String STOCK_SPLIT_FLAG = "stockSplitFlag";
        String MOODY = "moody";
        String SP = "sp";
        String FITCH = "fitch";
        String COMPOSITE = "composite";
        String COUPON_PREV_FLOAT = "couponPrevFloat";
        String LAST_MODIFIED_FIELD = "lastModified";
        String SINKABLE_FIELD = "sinkable";
        String IS_PERPETUAL_FIELD = "isPerpetual";
    }

    public interface DividendFields {
        String DVD_HIST_ALL = "DVD_HIST_ALL";
    }
}
