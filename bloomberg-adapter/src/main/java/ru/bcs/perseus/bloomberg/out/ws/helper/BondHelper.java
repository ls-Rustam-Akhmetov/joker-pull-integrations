package ru.bcs.perseus.bloomberg.out.ws.helper;

import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.AMT_ISSUED;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.AMT_OUTSTANDING;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.ANNOUNCE_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CALLABLE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CONVERTIBLE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.COUNTRY;
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
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.FINAL_MATURITY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.ID_BB_COMPANY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.ID_ISIN;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.INDUSTRY_GROUP;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.INDUSTRY_SECTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.INT_ACC;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.IS_PERPETUAL;
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
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.RUSSIAN_CB_LOMBARD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SECURITY_NAME;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SECURITY_SHORT_DES;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SETTLE_DT;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SINKABLE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SPREAD_DUR_ADJ_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.YAS_ASW_SPREAD;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.Z_SPRD_ASK;
import static ru.bcs.perseus.bloomberg.model.Constant.QuoteFields.PREV_FLT_CPN;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getBigDecimalFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getBooleanFromYNString;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getLocalDateFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getLongFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getStringFieldValue;
import static ru.bcs.perseus.bloomberg.util.BloombergUtils.getAccruedInterest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentType;
import ru.bcs.perseus.bloomberg.model.instrument.Rating;
import ru.bcs.perseus.bloomberg.model.instrument.bond.Bond;
import ru.bcs.perseus.bloomberg.model.instrument.bond.BondStatic;

public class BondHelper {

  private BondHelper() {
  }

  /**
   * @should return correct result
   * @should set correct id when there is no isin
   */
  public static Bond createBond(Map<String, String> fieldValues) {
    Instrument instrument = InstrumentHelper.createInstrument(fieldValues, InstrumentType.BOND);
    if (instrument == null) {
      return null;
    }

    BondStatic bondStatic = getBondStatic(fieldValues);
    Rating rating = RatingHelper.getRating(fieldValues);

    return new Bond(instrument, bondStatic, rating);
  }

  private static BondStatic getBondStatic(Map<String, String> fieldValues) {

    String isin = getStringFieldValue(fieldValues, ID_ISIN);
    String name = getStringFieldValue(fieldValues, NAME);
    String currency = getStringFieldValue(fieldValues, CRNCY);
    BigDecimal nominal = getBigDecimalFieldValue(fieldValues, PAR_AMT);
    LocalDate issueDate = getLocalDateFieldValue(fieldValues, ANNOUNCE_DT);
    Long issuedAmount = getLongFieldValue(fieldValues, AMT_ISSUED);
    String paymentRank = getStringFieldValue(fieldValues, PAYMENT_RANK);
    String industrySector = getStringFieldValue(fieldValues, INDUSTRY_SECTOR);
    Boolean putable = getBooleanFromYNString(fieldValues, PUTABLE);
    Boolean callable = getBooleanFromYNString(fieldValues, CALLABLE);
    LocalDate nextPutDate = getLocalDateFieldValue(fieldValues, NXT_PUT_DT);
    LocalDate nextCallDate = getLocalDateFieldValue(fieldValues, NXT_CALL_DT);
    BigDecimal coupon = getBigDecimalFieldValue(fieldValues, CPN);
    LocalDate couponNextDate = getLocalDateFieldValue(fieldValues, NXT_CPN_DT);
    LocalDate couponPrevDate = getLocalDateFieldValue(fieldValues, PREV_CPN_DT);
    BigDecimal couponFrequency = getBigDecimalFieldValue(fieldValues, CPN_FREQ);
    LocalDate maturity = getLocalDateFieldValue(fieldValues, MATURITY);
    BigDecimal duration = getBigDecimalFieldValue(fieldValues, DUR_ADJ_MID);
    LocalDate settlementDate = getLocalDateFieldValue(fieldValues, SETTLE_DT);
    BigDecimal intAcc = getBigDecimalFieldValue(fieldValues, INT_ACC);
    BigDecimal minimalLot = getBigDecimalFieldValue(fieldValues, MIN_PIECE);
    BigDecimal accruedInterest = getAccruedInterest(nominal, intAcc);
    BigDecimal assetSwapSpread = getBigDecimalFieldValue(fieldValues, YAS_ASW_SPREAD);
    BigDecimal marketCapitalization = getBigDecimalFieldValue(fieldValues, CUR_MKT_CAP);
    Long issuerId = getLongFieldValue(fieldValues, ID_BB_COMPANY);
    String securityName = getStringFieldValue(fieldValues, SECURITY_NAME);
    LocalDate finalMaturity = getLocalDateFieldValue(fieldValues, FINAL_MATURITY);
    BigDecimal zSpreadAsk = getBigDecimalFieldValue(fieldValues, Z_SPRD_ASK);
    BigDecimal outstandingAmount = getBigDecimalFieldValue(fieldValues, AMT_OUTSTANDING);
    BigDecimal macaulayDuration = getBigDecimalFieldValue(fieldValues, DUR_MID);
    String industryGroup = getStringFieldValue(fieldValues, INDUSTRY_GROUP);
    String dayCountConvention = getStringFieldValue(fieldValues, DAY_CNT_DES);
    String shortDescription = getStringFieldValue(fieldValues, SECURITY_SHORT_DES);
    Boolean ruLombardList = getBooleanFromYNString(fieldValues, RUSSIAN_CB_LOMBARD);
    String issuerCountry = getStringFieldValue(fieldValues, COUNTRY);
    String couponType = getStringFieldValue(fieldValues, CPN_TYP);
    BigDecimal durationAdjOasMid = getBigDecimalFieldValue(fieldValues, DUR_ADJ_OAS_MID);
    BigDecimal durationAdjMtyMid = getBigDecimalFieldValue(fieldValues, DUR_ADJ_MTY_MID);
    BigDecimal oasSpread = getBigDecimalFieldValue(fieldValues, OAS_SPREAD_DUR_MID);
    BigDecimal durationSpread = getBigDecimalFieldValue(fieldValues, SPREAD_DUR_ADJ_ASK);
    Boolean convertible = getBooleanFromYNString(fieldValues, CONVERTIBLE);
    BigDecimal minimalOrderIncrement = getBigDecimalFieldValue(fieldValues, MIN_INCREMENT);
    String couponPrevFloat = getStringFieldValue(fieldValues, PREV_FLT_CPN);
    Boolean sinkable = getBooleanFromYNString(fieldValues, SINKABLE);
    Boolean isPerpetual = getBooleanFromYNString(fieldValues, IS_PERPETUAL);


    return BondStatic.builder()
        .isin(isin)
        .issuer(name)
        .currency(currency)
        .nominal(nominal)
        .issueDate(issueDate)
        .issuedAmount(issuedAmount)
        .paymentRank(paymentRank)
        .industrySector(industrySector)
        .putable(putable)
        .callable(callable)
        .nextPutDate(nextPutDate)
        .nextCallDate(nextCallDate)
        .coupon(coupon)
        .couponNextDate(couponNextDate)
        .couponPrevDate(couponPrevDate)
        .couponFrequency(couponFrequency)
        .maturity(maturity)
        .duration(duration)
        .settlementDate(settlementDate)
        .accruedInterest(accruedInterest)
        .accruedInterestT0(accruedInterest)
        .minimalLot(minimalLot)
        .assetSwapSpread(assetSwapSpread)
        .marketCapitalization(marketCapitalization)
        .issuerId(issuerId)
        .securityName(securityName)
        .finalMaturity(finalMaturity)
        .zSpreadAsk(zSpreadAsk)
        .outstandingAmount(outstandingAmount)
        .macaulayDuration(macaulayDuration)
        .industryGroup(industryGroup)
        .dayCountConvention(dayCountConvention)
        .shortDescription(shortDescription)
        .ruLombardList(ruLombardList)
        .issuerCountry(issuerCountry)
        .couponType(couponType)
        .durationAdjOasMid(durationAdjOasMid)
        .durationAdjMtyMid(durationAdjMtyMid)
        .oasSpread(oasSpread)
        .durationSpread(durationSpread)
        .convertible(convertible)
        .minimalOrderIncrement(minimalOrderIncrement)
        .couponPrevFloat(couponPrevFloat)
        .sinkable(sinkable)
        .isPerpetual(isPerpetual)
        .build();
  }
}
