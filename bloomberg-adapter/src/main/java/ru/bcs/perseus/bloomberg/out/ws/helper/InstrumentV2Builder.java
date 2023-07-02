package ru.bcs.perseus.bloomberg.out.ws.helper;

import static java.util.stream.Collectors.toMap;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.AMT_ISSUED;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.AMT_OUTSTANDING;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.ANNOUNCE_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.BB_COMPOSITE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.BLOOMBERG_CFI_CODE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CALLABLE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CFI_CODE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CNTRY_OF_DOMICILE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.COMPOSITE_FIGI;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CONVERTIBLE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.COUNTRY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.COUNTRY_ISO;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CPN;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CPN_FREQ;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CPN_TYP;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CRNCY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CUR_MKT_CAP;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DAY_CNT_DES;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DUR_ADJ_MID;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DUR_ADJ_MTY_MID;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DUR_ADJ_OAS_MID;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DUR_MID;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DVD_CRNCY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DVD_SH_12M;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.EXCHANGE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.FIGI;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.FINAL_MATURITY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.ID_BB_COMPANY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.ID_ISIN;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.INDUSTRY_GROUP;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.INDUSTRY_SECTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.INT_ACC;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.IS_PERPETUAL;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MARKET_SECTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MARKET_SECTOR_DES;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MATURITY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MIN_INCREMENT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MIN_PIECE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.NAME;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.NXT_CALL_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.NXT_CPN_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.NXT_PUT_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.OAS_SPREAD_DUR_MID;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PAR_AMT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PAYMENT_RANK;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PREV_CPN_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PUTABLE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PX_QUOTE_LOT_SIZE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PX_ROUND_LOT_SIZE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PX_TRADE_LOT_SIZE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.QUOTE_FACTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RTG_FITCH_NO_WATCH;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RTG_MOODY_NO_WATCH;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RTG_SP_NO_WATCH;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RUSSIAN_CB_LOMBARD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SECURITY_DES;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SECURITY_NAME;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SECURITY_SHORT_DES;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SETTLE_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SINKABLE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SPREAD_DUR_ADJ_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.YAS_ASW_SPREAD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.Z_SPRD_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ACCRUED_INTEREST_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ASSET_SWAP_SPREAD_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.BLOOMBERG_CFI_CODE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.BL_EXCHANGE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.CALLABLE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.CFI_CODE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COMPOSITE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COMPOSITE_FIGI_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.CONVERTIBLE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUNTRY_DOMICILE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUNTRY_ISO_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUPON_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUPON_FREQUENCY_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUPON_NEXT_DATE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUPON_PREV_DATE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUPON_PREV_FLOAT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.COUPON_TYPE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.CURRENCY_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.CURRENCY_QUOTE_FACTOR_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DAY_COUNT_CONVENTION_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_CASH_GROSS_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_CASH_RECORD_DATE_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_CURRENCY_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_CURRENCY_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_EX_DATE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_EX_FLAG;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_RECORD_DATE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_SHARE_12M_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_SHARE_LIST;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_SPLIT_ADJUST_FACTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_SPLIT_RECORD_DATE_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_STOCK_ADJUST_FACTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_STOCK_FLAG;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_STOCK_RECORD_DATE_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DIVIDEND_STOCK_TYPE_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DURATION_ADJ_MTY_MID_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DURATION_ADJ_OAS_MID_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DURATION_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.DURATION_SPREAD_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.FINAL_MATURITY_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.FITCH;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.INDUSTRY_GROUP_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.INDUSTRY_SECTOR_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ISIN_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ISSUED_AMOUNT_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ISSUER_COUNTRY_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ISSUER_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ISSUER_ID_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ISSUE_DATE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.IS_PERPETUAL_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.LAST_MODIFIED_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MACAULAY_DURATION_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MARKET_CAPITALIZATION_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MARKET_SECTOR_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MARKET_SECTOR_ID_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MATURITY_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MINIMAL_LOT_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MINIMAL_ORDER_INCREMENT_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.MOODY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.NEXT_CALL_DATE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.NEXT_PUT_DATE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.NOMINAL_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.OAS_SPREAD_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.OUTSTANDING_AMOUNT_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.PAYMENT_RANK_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.PUTABLE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.QUOTE_LOT_SIZE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.ROUND_LOT_SIZE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.RU_LOMBARD_LIST_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.SECURITY_NAME_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.SETTLEMENT_DATE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.SHORT_DESCRIPTION_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.SINKABLE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.SOURCE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.SP;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.STOCK_SPLIT_FLAG;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.TICKER;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.TRADE_LOT_SIZE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.TYPE_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.USER_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFieldsReadable.Z_SPREAD_ASK_FIELD;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.DVD_EX_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.DVD_RECORD_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.DVD_SH_LAST;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_CASH_CRNCY_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_CASH_GROSS_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_CASH_RECORD_DT_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_EX_FLAG;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_SPL_ADJ_FCT_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_SPL_EX_FLAG;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_SPL_RECORD_DT_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_STK_ADJ_FCT_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_STK_EX_FLAG;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_STK_RECORD_DT_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.EQY_DVD_STK_TYP_NEXT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PREV_FLT_CPN;
import static ru.bcs.perseus.bloomberg.model.Constant.SOURCE;
import static ru.bcs.perseus.bloomberg.model.Constant.USERNAME;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getBigDecimalFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getBooleanFromYNString;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getIntFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getLocalDateFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getLongFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getMarketSector;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getStringFieldValue;
import static ru.bcs.perseus.bloomberg.util.BloombergUtils.getAccruedInterest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import ru.bcs.perseus.bloomberg.model.MarketSector;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentType;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentV2;

@Slf4j
public class InstrumentV2Builder {

  private InstrumentV2Builder() {
  }

  public static InstrumentV2 buildInstrumentV2(Map<String, String> fieldValues) {
    MarketSector marketSector = getMarketSector(fieldValues);
    if (marketSector == null) {
      log.warn("Not created instrument from response field {} because market sector is unknown",
          fieldValues);
      return null;
    }

    String blExchange = getStringFieldValue(fieldValues, EXCHANGE);
    if (blExchange == null) {
      log.warn("Not created instrument from response field {} because field EXCHANGE is null",
          fieldValues);
      return null;
    }

    InstrumentType type = mapMarketSectorToType(marketSector);
    if (type == null) {
      log.warn("Not created instrument from response field {} because instrument type is null",
          fieldValues);
      return null;
    }

    InstrumentV2 instrumentV2 = new InstrumentV2();

    instrumentV2.setId(getStringFieldValue(fieldValues, FIGI));

    BigDecimal nominal = getBigDecimalFieldValue(fieldValues, PAR_AMT);
    BigDecimal intAcc = getBigDecimalFieldValue(fieldValues, INT_ACC);
    BigDecimal accruedInterest = getAccruedInterest(nominal, intAcc);

    Map<String, Object> fields = new HashMap<>();
    fields.put(ISIN_FIELD, getStringFieldValue(fieldValues, ID_ISIN));
    fields.put(MATURITY_FIELD, getLocalDateFieldValue(fieldValues, MATURITY));
    fields.put(TICKER, getStringFieldValue(fieldValues, SECURITY_DES));
    fields.put(COMPOSITE_FIGI_FIELD, getStringFieldValue(fieldValues, COMPOSITE_FIGI));
    fields.put(BL_EXCHANGE_FIELD, blExchange);
    fields.put(MARKET_SECTOR_FIELD, getStringFieldValue(fieldValues, MARKET_SECTOR_DES));
    fields.put(MARKET_SECTOR_ID_FIELD, getIntFieldValue(fieldValues, MARKET_SECTOR));
    fields.put(CURRENCY_FIELD, getStringFieldValue(fieldValues, CRNCY));
    fields.put(CFI_CODE_FIELD, getStringFieldValue(fieldValues, CFI_CODE));
    fields.put(BLOOMBERG_CFI_CODE_FIELD, getStringFieldValue(fieldValues, BLOOMBERG_CFI_CODE));
    fields.put(COUNTRY_ISO_FIELD, getStringFieldValue(fieldValues, COUNTRY_ISO));
    fields.put(COUNTRY_DOMICILE_FIELD, getStringFieldValue(fieldValues, CNTRY_OF_DOMICILE));
    fields.put(TRADE_LOT_SIZE_FIELD, getBigDecimalFieldValue(fieldValues, PX_TRADE_LOT_SIZE));
    fields.put(QUOTE_LOT_SIZE_FIELD, getBigDecimalFieldValue(fieldValues, PX_QUOTE_LOT_SIZE));
    fields.put(ROUND_LOT_SIZE_FIELD, getBigDecimalFieldValue(fieldValues, PX_ROUND_LOT_SIZE));
    fields.put(ISSUER_FIELD, getStringFieldValue(fieldValues, NAME));
    fields.put(NOMINAL_FIELD, nominal);
    fields.put(ISSUE_DATE_FIELD, getLocalDateFieldValue(fieldValues, ANNOUNCE_DT));
    fields.put(ISSUED_AMOUNT_FIELD, getLongFieldValue(fieldValues, AMT_ISSUED));
    fields.put(PAYMENT_RANK_FIELD, getStringFieldValue(fieldValues, PAYMENT_RANK));
    fields.put(INDUSTRY_SECTOR_FIELD, getStringFieldValue(fieldValues, INDUSTRY_SECTOR));
    fields.put(PUTABLE_FIELD, getBooleanFromYNString(fieldValues, PUTABLE));
    fields.put(CALLABLE_FIELD, getBooleanFromYNString(fieldValues, CALLABLE));
    fields.put(NEXT_PUT_DATE_FIELD, getLocalDateFieldValue(fieldValues, NXT_PUT_DT));
    fields.put(NEXT_CALL_DATE_FIELD, getLocalDateFieldValue(fieldValues, NXT_CALL_DT));
    fields.put(COUPON_FIELD, getBigDecimalFieldValue(fieldValues, CPN));
    fields.put(COUPON_NEXT_DATE_FIELD, getLocalDateFieldValue(fieldValues, NXT_CPN_DT));
    fields.put(COUPON_PREV_DATE_FIELD, getLocalDateFieldValue(fieldValues, PREV_CPN_DT));
    fields.put(COUPON_FREQUENCY_FIELD, getBigDecimalFieldValue(fieldValues, CPN_FREQ));
    fields.put(DURATION_FIELD, getBigDecimalFieldValue(fieldValues, DUR_ADJ_MID));
    fields.put(SETTLEMENT_DATE_FIELD, getLocalDateFieldValue(fieldValues, SETTLE_DT));
    fields.put(ACCRUED_INTEREST_FIELD, accruedInterest);
    fields.put(MINIMAL_LOT_FIELD, getBigDecimalFieldValue(fieldValues, MIN_PIECE));
    fields.put(ASSET_SWAP_SPREAD_FIELD, getBigDecimalFieldValue(fieldValues, YAS_ASW_SPREAD));
    fields.put(MARKET_CAPITALIZATION_FIELD, getBigDecimalFieldValue(fieldValues, CUR_MKT_CAP));
    fields.put(ISSUER_ID_FIELD, getLongFieldValue(fieldValues, ID_BB_COMPANY));
    fields.put(SECURITY_NAME_FIELD, getStringFieldValue(fieldValues, SECURITY_NAME));
    fields.put(FINAL_MATURITY_FIELD, getLocalDateFieldValue(fieldValues, FINAL_MATURITY));
    fields.put(Z_SPREAD_ASK_FIELD, getBigDecimalFieldValue(fieldValues, Z_SPRD_ASK));
    fields.put(OUTSTANDING_AMOUNT_FIELD, getBigDecimalFieldValue(fieldValues, AMT_OUTSTANDING));
    fields.put(MACAULAY_DURATION_FIELD, getBigDecimalFieldValue(fieldValues, DUR_MID));
    fields.put(INDUSTRY_GROUP_FIELD, getStringFieldValue(fieldValues, INDUSTRY_GROUP));
    fields.put(DAY_COUNT_CONVENTION_FIELD, getStringFieldValue(fieldValues, DAY_CNT_DES));
    fields.put(SHORT_DESCRIPTION_FIELD, getStringFieldValue(fieldValues, SECURITY_SHORT_DES));
    fields.put(RU_LOMBARD_LIST_FIELD, getBooleanFromYNString(fieldValues, RUSSIAN_CB_LOMBARD));
    fields.put(ISSUER_COUNTRY_FIELD, getStringFieldValue(fieldValues, COUNTRY));
    fields.put(COUPON_TYPE_FIELD, getStringFieldValue(fieldValues, CPN_TYP));
    fields.put(DURATION_ADJ_OAS_MID_FIELD, getBigDecimalFieldValue(fieldValues, DUR_ADJ_OAS_MID));
    fields.put(DURATION_ADJ_MTY_MID_FIELD, getBigDecimalFieldValue(fieldValues, DUR_ADJ_MTY_MID));
    fields.put(OAS_SPREAD_FIELD, getBigDecimalFieldValue(fieldValues, OAS_SPREAD_DUR_MID));
    fields.put(DURATION_SPREAD_FIELD, getBigDecimalFieldValue(fieldValues, SPREAD_DUR_ADJ_ASK));
    fields.put(CONVERTIBLE_FIELD, getBooleanFromYNString(fieldValues, CONVERTIBLE));
    fields.put(DIVIDEND_CURRENCY_FIELD, getStringFieldValue(fieldValues, DVD_CRNCY));
    fields.put(DIVIDEND_SHARE_12M_FIELD, getBigDecimalFieldValue(fieldValues, DVD_SH_12M));
    fields.put(SOURCE_FIELD, SOURCE);
    fields.put(USER_FIELD, USERNAME);
    fields.put(TYPE_FIELD, type);
    fields.put(CURRENCY_QUOTE_FACTOR_FIELD, getStringFieldValue(fieldValues, QUOTE_FACTOR));
    fields.put(MINIMAL_ORDER_INCREMENT_FIELD, getBigDecimalFieldValue(fieldValues, MIN_INCREMENT));
    fields.put(DIVIDEND_EX_DATE, getLocalDateFieldValue(fieldValues, DVD_EX_DT));
    fields.put(DIVIDEND_RECORD_DATE, getLocalDateFieldValue(fieldValues, DVD_RECORD_DT));
    fields.put(DIVIDEND_SHARE_LIST, getBigDecimalFieldValue(fieldValues, DVD_SH_LAST));
    fields.put(DIVIDEND_CASH_GROSS_NEXT, getBigDecimalFieldValue(fieldValues, EQY_DVD_CASH_GROSS_NEXT));
    fields.put(DIVIDEND_CASH_RECORD_DATE_NEXT, getLocalDateFieldValue(fieldValues, EQY_DVD_CASH_RECORD_DT_NEXT));
    fields.put(DIVIDEND_CURRENCY_NEXT, getStringFieldValue(fieldValues, EQY_DVD_CASH_CRNCY_NEXT));
    fields.put(DIVIDEND_STOCK_RECORD_DATE_NEXT, getLocalDateFieldValue(fieldValues, EQY_DVD_STK_RECORD_DT_NEXT));
    fields.put(DIVIDEND_STOCK_TYPE_NEXT, getStringFieldValue(fieldValues, EQY_DVD_STK_TYP_NEXT));
    fields.put(DIVIDEND_STOCK_ADJUST_FACTOR, getStringFieldValue(fieldValues, EQY_DVD_STK_ADJ_FCT_NEXT));
    fields.put(DIVIDEND_SPLIT_RECORD_DATE_NEXT, getLocalDateFieldValue(fieldValues, EQY_DVD_SPL_RECORD_DT_NEXT));
    fields.put(DIVIDEND_SPLIT_ADJUST_FACTOR, getStringFieldValue(fieldValues, EQY_DVD_SPL_ADJ_FCT_NEXT));
    fields.put(DIVIDEND_EX_FLAG ,getBooleanFromYNString(fieldValues, EQY_DVD_EX_FLAG));
    fields.put(DIVIDEND_STOCK_FLAG ,getBooleanFromYNString(fieldValues, EQY_DVD_STK_EX_FLAG));
    fields.put(STOCK_SPLIT_FLAG ,getBooleanFromYNString(fieldValues, EQY_DVD_SPL_EX_FLAG));
    fields.put(MOODY, getStringFieldValue(fieldValues, RTG_MOODY_NO_WATCH));
    fields.put(SP, getStringFieldValue(fieldValues, RTG_SP_NO_WATCH));
    fields.put(FITCH, getStringFieldValue(fieldValues, RTG_FITCH_NO_WATCH));
    fields.put(COMPOSITE, getStringFieldValue(fieldValues, BB_COMPOSITE));
    fields.put(COUPON_PREV_FLOAT, getStringFieldValue(fieldValues, PREV_FLT_CPN));
    fields.put(LAST_MODIFIED_FIELD, LocalDateTime.now());
    fields.put(SINKABLE_FIELD, getBooleanFromYNString(fieldValues, SINKABLE));
    fields.put(IS_PERPETUAL_FIELD, getBooleanFromYNString(fieldValues, IS_PERPETUAL));

    Map<String, Object> resultFields = fields.entrySet().stream()
        .filter(field -> field.getValue() != null)
        .collect(toMap(Entry::getKey, Entry::getValue));

    instrumentV2.setFields(resultFields);
    return instrumentV2;
  }

  private static InstrumentType mapMarketSectorToType(MarketSector marketSector) {
    switch (marketSector) {
      case EQUITY:
        return InstrumentType.EQUITY;
      case CORP:
      case GOVT:
        return InstrumentType.BOND;
      default:
        return null;
    }
  }

}
