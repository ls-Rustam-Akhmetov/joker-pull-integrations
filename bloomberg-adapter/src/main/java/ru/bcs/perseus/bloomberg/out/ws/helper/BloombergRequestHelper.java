package ru.bcs.perseus.bloomberg.out.ws.helper;

import static ru.bcs.perseus.bloomberg.model.Constant.DividendFields.DVD_HIST_ALL;
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
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.DEFAULTED;
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
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PRINCIPAL_FACTOR;
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
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SINKABLE_PAR_MARKET;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SPREAD_DUR_ADJ_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.TICKER;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.TRADE_DT_ACC_INT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.YAS_ASW_SPREAD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.Z_SPRD_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.CHG_PCT_1D;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.CHG_PCT_1M;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.CHG_PCT_1YR;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.CHG_PCT_6M;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.CHG_PCT_WTD;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.DVD_DECLARED_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.DVD_EX_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.DVD_PAY_DT;
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
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_BID;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_HIGH;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_LAST;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_LAST_EOD;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_LOW;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PX_MID;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.QUOTED_CRNCY;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.VWAP_ALL_TRADES;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.YLD_CNV_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.YLD_CNV_BID;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.YLD_CNV_LAST;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.YLD_CNV_MID;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.YLD_CNV_MID_EOD;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.YLD_YTM_MID;
import static ru.bcs.perseus.bloomberg.model.db.RequestType.DIVIDENDS;
import static ru.bcs.perseus.bloomberg.util.CurrencyHelper.isCurrency;

import com.bloomberg.services.dlws.Fields;
import com.bloomberg.services.dlws.GetDataHeaders;
import com.bloomberg.services.dlws.Instrument;
import com.bloomberg.services.dlws.InstrumentType;
import com.bloomberg.services.dlws.Instruments;
import com.bloomberg.services.dlws.MarketSector;
import com.bloomberg.services.dlws.SubmitGetDataRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import ru.bcs.perseus.bloomberg.model.Constant;
import ru.bcs.perseus.bloomberg.model.Exchange;
import ru.bcs.perseus.bloomberg.model.db.RequestType;
import ru.bcs.perseus.bloomberg.model.db.Sync;
import ru.bcs.perseus.bloomberg.model.exception.InternalServerErrorException;

public class BloombergRequestHelper {

  private static final Pattern ISIN_PATTERN = Pattern.compile("[a-zA-Z]{2}[a-zA-Z0-9]{9}[0-9]{1}");

  private BloombergRequestHelper() {
  }

  public static SubmitGetDataRequest makeSubmitGetDataRequest(List<Sync> syncs,
      RequestType requestType) {
    List<Instrument> instrumentList = syncs.stream()
        .map(val -> DIVIDENDS == requestType ? makeDividendInstrument(val) : makeInstrument(val))
        .collect(Collectors.toList());

    Instruments instruments = new Instruments();
    instruments.getInstrument().addAll(instrumentList);

    SubmitGetDataRequest request = new SubmitGetDataRequest();
    request.setFields(makeFields(requestType));
    request.setHeaders(makeHeaders(requestType));
    request.setInstruments(instruments);

    return request;
  }

  private static GetDataHeaders makeHeaders(RequestType requestType) {
    GetDataHeaders headers = new GetDataHeaders();
    switch (requestType) {
      case COMBINED:
      case INSTRUMENT:
        headers.setSecmaster(true);
        headers.setPricing(true);
        headers.setDerived(true);
        break;
      case QUOTE:
        headers.setPricing(true);
        headers.setSecmaster(true);
        headers.setDerived(true);
        break;
      case DIVIDENDS:
        headers.setHistorical(true);
        break;
      default:
        throw new InternalServerErrorException("no such type processing:" + requestType);
    }

    return headers;
  }

  public static Instrument makeHistoryInstrument(Sync sync) {
    Instrument instrument = new Instrument();

    if (isCurrency(sync.getInstrumentType())) {
      instrument.setId(sync.getIsin());
      instrument.setYellowkey(MarketSector.CURNCY);
    } else {
      instrument.setId(sync.getFigi());
      instrument.setType(InstrumentType.BB_GLOBAL);
    }
    return instrument;
  }

  private static Instrument makeInstrument(Sync sync) {
    String id = sync.getExchange() == Exchange.OTC ? sync.getIsin()
        : sync.getIsin() + " " + sync.getExchange();

    Instrument instrument = new Instrument();
    instrument.setId(id);

    if (sync.getIsin() == null) {
      return instrument;
    }

    boolean isCurrency = isCurrency(sync.getIsin());
    if (isCurrency) {
      instrument.setYellowkey(MarketSector.CURNCY);
    } else {
      boolean isIsin = ISIN_PATTERN.matcher(sync.getIsin()).matches();
      instrument.setType(isIsin ? InstrumentType.ISIN : InstrumentType.TICKER);
    }

    return instrument;
  }

  private static Instrument makeDividendInstrument(Sync sync) {
    String id = sync.getFigi();
    Instrument instrument = new Instrument();
    instrument.setId(id);
    instrument.setType(InstrumentType.BB_GLOBAL);
    instrument.setYellowkey(MarketSector.EQUITY);
    return instrument;
  }

  private static Fields fillFieldsForInstrumentRequest(Fields fields) {
    List<String> fieldNames = fields.getField();

    fieldNames.add(MARKET_SECTOR_DES);
    fieldNames.add(SECURITY_DES);
    fieldNames.add(TICKER);
    fieldNames.add(ID_ISIN);

    fieldNames.add(NAME);
    fieldNames.add(CRNCY);
    fieldNames.add(PAR_AMT);
    fieldNames.add(ANNOUNCE_DT);
    fieldNames.add(AMT_ISSUED);
    fieldNames.add(PAYMENT_RANK);
    fieldNames.add(INDUSTRY_SECTOR);
    fieldNames.add(PUTABLE);
    fieldNames.add(CALLABLE);
    fieldNames.add(CPN_FREQ);
    fieldNames.add(MATURITY);

    fieldNames.add(NXT_PUT_DT);
    fieldNames.add(NXT_CALL_DT);
    fieldNames.add(CPN);
    fieldNames.add(NXT_CPN_DT);
    fieldNames.add(PREV_CPN_DT);
    fieldNames.add(DUR_MID);
    fieldNames.add(SETTLE_DT);
    fieldNames.add(INT_ACC);
    fieldNames.add(RTG_MOODY_NO_WATCH);
    fieldNames.add(RTG_SP_NO_WATCH);
    fieldNames.add(RTG_FITCH_NO_WATCH);
    fieldNames.add(BB_COMPOSITE);
    fieldNames.add(MIN_PIECE);

    fieldNames.add(FIGI);
    fieldNames.add(COMPOSITE_FIGI);
    fieldNames.add(EXCHANGE);

    fieldNames.add(MARKET_SECTOR);
    fieldNames.add(YAS_ASW_SPREAD);

    fieldNames.add(CUR_MKT_CAP);
    fieldNames.add(ID_BB_COMPANY);
    fieldNames.add(SECURITY_NAME);
    fieldNames.add(FINAL_MATURITY);
    fieldNames.add(Z_SPRD_ASK);
    fieldNames.add(AMT_OUTSTANDING);
    fieldNames.add(DUR_ADJ_MID);
    fieldNames.add(INDUSTRY_GROUP);
    fieldNames.add(DAY_CNT_DES);
    fieldNames.add(SECURITY_SHORT_DES);
    fieldNames.add(RUSSIAN_CB_LOMBARD);
    fieldNames.add(COUNTRY);
    fieldNames.add(CPN_TYP);
    fieldNames.add(DUR_ADJ_OAS_MID);
    fieldNames.add(DUR_ADJ_MTY_MID);
    fieldNames.add(OAS_SPREAD_DUR_MID);
    fieldNames.add(SPREAD_DUR_ADJ_ASK);
    fieldNames.add(CONVERTIBLE);

    fieldNames.add(DVD_CRNCY);
    fieldNames.add(DVD_SH_12M);
    fieldNames.add(CFI_CODE);
    fieldNames.add(BLOOMBERG_CFI_CODE);
    fieldNames.add(COUNTRY_ISO);
    fieldNames.add(CNTRY_OF_DOMICILE);
    fieldNames.add(PX_TRADE_LOT_SIZE);
    fieldNames.add(PX_QUOTE_LOT_SIZE);
    fieldNames.add(PX_ROUND_LOT_SIZE);
    fieldNames.add(QUOTE_FACTOR);
    fieldNames.add(MIN_INCREMENT);
    fieldNames.add(PREV_FLT_CPN);
    fieldNames.add(DVD_EX_DT);
    fieldNames.add(DVD_RECORD_DT);
    fieldNames.add(DVD_SH_LAST);
    fieldNames.add(EQY_DVD_CASH_GROSS_NEXT);
    fieldNames.add(EQY_DVD_CASH_RECORD_DT_NEXT);
    fieldNames.add(EQY_DVD_CASH_CRNCY_NEXT);
    fieldNames.add(EQY_DVD_STK_RECORD_DT_NEXT);
    fieldNames.add(EQY_DVD_STK_TYP_NEXT);
    fieldNames.add(EQY_DVD_STK_ADJ_FCT_NEXT);
    fieldNames.add(EQY_DVD_SPL_RECORD_DT_NEXT);
    fieldNames.add(EQY_DVD_SPL_ADJ_FCT_NEXT);
    fieldNames.add(EQY_DVD_EX_FLAG);
    fieldNames.add(EQY_DVD_STK_EX_FLAG);
    fieldNames.add(EQY_DVD_SPL_EX_FLAG);
    fieldNames.add(DUR_ADJ_MID);
    fieldNames.add(SINKABLE);
    fieldNames.add(IS_PERPETUAL);

    return fields;
  }

  private static Fields fillFieldsForQuoteRequest(Fields fields) {
    List<String> fieldNames = fields.getField();

    fieldNames.add(Constant.QuoteFields.MARKET_SECTOR_DES);
    fieldNames.add(Constant.QuoteFields.TICKER);
    fieldNames.add(Constant.QuoteFields.ID_ISIN);
    fieldNames.add(Constant.QuoteFields.FIGI);
    fieldNames.add(PX_LAST_EOD);
    fieldNames.add(PX_LAST);
    fieldNames.add(PX_BID);
    fieldNames.add(PX_ASK);
    fieldNames.add(PX_LOW);
    fieldNames.add(PX_HIGH);
    fieldNames.add(QUOTED_CRNCY);
    fieldNames.add(YLD_CNV_BID);
    fieldNames.add(YLD_CNV_ASK);
    fieldNames.add(YLD_CNV_LAST);
    fieldNames.add(VWAP_ALL_TRADES);

    fieldNames.add(YLD_YTM_MID);
    fieldNames.add(PX_MID);
    fieldNames.add(CHG_PCT_1D);
    fieldNames.add(CHG_PCT_WTD);
    fieldNames.add(CHG_PCT_1M);
    fieldNames.add(CHG_PCT_6M);
    fieldNames.add(CHG_PCT_1YR);
    fieldNames.add(YLD_CNV_MID);

    fieldNames.add(YLD_CNV_MID_EOD);
    fieldNames.add(CPN);
    fieldNames.add(NXT_CPN_DT);
    fieldNames.add(CPN_FREQ);
    fieldNames.add(PREV_FLT_CPN);
    fieldNames.add(DVD_EX_DT);
    fieldNames.add(DVD_RECORD_DT);
    fieldNames.add(DVD_SH_LAST);
    fieldNames.add(EQY_DVD_CASH_GROSS_NEXT);
    fieldNames.add(EQY_DVD_CASH_RECORD_DT_NEXT);
    fieldNames.add(EQY_DVD_CASH_CRNCY_NEXT);
    fieldNames.add(EQY_DVD_STK_RECORD_DT_NEXT);
    fieldNames.add(EQY_DVD_STK_TYP_NEXT);
    fieldNames.add(EQY_DVD_STK_ADJ_FCT_NEXT);
    fieldNames.add(EQY_DVD_SPL_RECORD_DT_NEXT);
    fieldNames.add(EQY_DVD_SPL_ADJ_FCT_NEXT);
    fieldNames.add(DVD_PAY_DT);
    fieldNames.add(DVD_DECLARED_DT);
    fieldNames.add(EQY_DVD_EX_FLAG);
    fieldNames.add(EQY_DVD_STK_EX_FLAG);
    fieldNames.add(EQY_DVD_SPL_EX_FLAG);
    fieldNames.add(DUR_ADJ_MID);
    fieldNames.add(INT_ACC);
    fieldNames.add(PAR_AMT);
    fieldNames.add(PREV_CPN_DT);
    fieldNames.add(TRADE_DT_ACC_INT);
    fieldNames.add(PRINCIPAL_FACTOR);
    fieldNames.add(SINKABLE_PAR_MARKET);
    fieldNames.add(DEFAULTED);

    return fields;
  }

  private static Fields fillDividendFields(final Fields fields) {
    final List<String> fieldNames = fields.getField();

    fieldNames.add(DVD_HIST_ALL);

    return fields;
  }

  private static Fields fillCombinedFields() {
    Fields fields = new Fields();
    fillFieldsForInstrumentRequest(fields);
    fillFieldsForQuoteRequest(fields);

    Set<String> distinctFields = new HashSet<>(fields.getField());
    Fields resultFields = new Fields();
    resultFields.getField().addAll(distinctFields);
    return resultFields;
  }

  private static Fields makeFields(RequestType requestType) {
    switch (requestType) {
      case INSTRUMENT:
        return fillFieldsForInstrumentRequest(new Fields());
      case QUOTE:
        return fillFieldsForQuoteRequest(new Fields());
      case COMBINED:
        return fillCombinedFields();
      case DIVIDENDS:
        return fillDividendFields(new Fields());
      default:
        throw new InternalServerErrorException("no such type processing:" + requestType);
    }
  }
}
