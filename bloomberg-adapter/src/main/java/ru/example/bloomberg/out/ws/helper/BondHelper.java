package ru.example.bloomberg.out.ws.helper;

import ru.example.bloomberg.model.instrument.Instrument;
import ru.example.bloomberg.model.instrument.InstrumentType;
import ru.example.bloomberg.model.instrument.Rating;
import ru.example.bloomberg.model.instrument.bond.Bond;
import ru.example.bloomberg.model.instrument.bond.BondStatic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static ru.example.bloomberg.model.Constant.InstrumentFields.*;
import static ru.example.bloomberg.model.Constant.QuoteFields.PREV_FLT_CPN;
import static ru.example.bloomberg.util.BloombergUtils.getAccruedInterest;

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

        String isin = FieldHelper.getStringFieldValue(fieldValues, ID_ISIN);
        String name = FieldHelper.getStringFieldValue(fieldValues, NAME);
        String currency = FieldHelper.getStringFieldValue(fieldValues, CRNCY);
        BigDecimal nominal = FieldHelper.getBigDecimalFieldValue(fieldValues, PAR_AMT);
        LocalDate issueDate = FieldHelper.getLocalDateFieldValue(fieldValues, ANNOUNCE_DT);
        Long issuedAmount = FieldHelper.getLongFieldValue(fieldValues, AMT_ISSUED);
        String paymentRank = FieldHelper.getStringFieldValue(fieldValues, PAYMENT_RANK);
        String industrySector = FieldHelper.getStringFieldValue(fieldValues, INDUSTRY_SECTOR);
        Boolean putable = FieldHelper.getBooleanFromYNString(fieldValues, PUTABLE);
        Boolean callable = FieldHelper.getBooleanFromYNString(fieldValues, CALLABLE);
        LocalDate nextPutDate = FieldHelper.getLocalDateFieldValue(fieldValues, NXT_PUT_DT);
        LocalDate nextCallDate = FieldHelper.getLocalDateFieldValue(fieldValues, NXT_CALL_DT);
        BigDecimal coupon = FieldHelper.getBigDecimalFieldValue(fieldValues, CPN);
        LocalDate couponNextDate = FieldHelper.getLocalDateFieldValue(fieldValues, NXT_CPN_DT);
        LocalDate couponPrevDate = FieldHelper.getLocalDateFieldValue(fieldValues, PREV_CPN_DT);
        BigDecimal couponFrequency = FieldHelper.getBigDecimalFieldValue(fieldValues, CPN_FREQ);
        LocalDate maturity = FieldHelper.getLocalDateFieldValue(fieldValues, MATURITY);
        BigDecimal duration = FieldHelper.getBigDecimalFieldValue(fieldValues, DUR_ADJ_MID);
        LocalDate settlementDate = FieldHelper.getLocalDateFieldValue(fieldValues, SETTLE_DT);
        BigDecimal intAcc = FieldHelper.getBigDecimalFieldValue(fieldValues, INT_ACC);
        BigDecimal minimalLot = FieldHelper.getBigDecimalFieldValue(fieldValues, MIN_PIECE);
        BigDecimal accruedInterest = getAccruedInterest(nominal, intAcc);
        BigDecimal assetSwapSpread = FieldHelper.getBigDecimalFieldValue(fieldValues, YAS_ASW_SPREAD);
        BigDecimal marketCapitalization = FieldHelper.getBigDecimalFieldValue(fieldValues, CUR_MKT_CAP);
        Long issuerId = FieldHelper.getLongFieldValue(fieldValues, ID_BB_COMPANY);
        String securityName = FieldHelper.getStringFieldValue(fieldValues, SECURITY_NAME);
        LocalDate finalMaturity = FieldHelper.getLocalDateFieldValue(fieldValues, FINAL_MATURITY);
        BigDecimal zSpreadAsk = FieldHelper.getBigDecimalFieldValue(fieldValues, Z_SPRD_ASK);
        BigDecimal outstandingAmount = FieldHelper.getBigDecimalFieldValue(fieldValues, AMT_OUTSTANDING);
        BigDecimal macaulayDuration = FieldHelper.getBigDecimalFieldValue(fieldValues, DUR_MID);
        String industryGroup = FieldHelper.getStringFieldValue(fieldValues, INDUSTRY_GROUP);
        String dayCountConvention = FieldHelper.getStringFieldValue(fieldValues, DAY_CNT_DES);
        String shortDescription = FieldHelper.getStringFieldValue(fieldValues, SECURITY_SHORT_DES);
        Boolean ruLombardList = FieldHelper.getBooleanFromYNString(fieldValues, RUSSIAN_CB_LOMBARD);
        String issuerCountry = FieldHelper.getStringFieldValue(fieldValues, COUNTRY);
        String couponType = FieldHelper.getStringFieldValue(fieldValues, CPN_TYP);
        BigDecimal durationAdjOasMid = FieldHelper.getBigDecimalFieldValue(fieldValues, DUR_ADJ_OAS_MID);
        BigDecimal durationAdjMtyMid = FieldHelper.getBigDecimalFieldValue(fieldValues, DUR_ADJ_MTY_MID);
        BigDecimal oasSpread = FieldHelper.getBigDecimalFieldValue(fieldValues, OAS_SPREAD_DUR_MID);
        BigDecimal durationSpread = FieldHelper.getBigDecimalFieldValue(fieldValues, SPREAD_DUR_ADJ_ASK);
        Boolean convertible = FieldHelper.getBooleanFromYNString(fieldValues, CONVERTIBLE);
        BigDecimal minimalOrderIncrement = FieldHelper.getBigDecimalFieldValue(fieldValues, MIN_INCREMENT);
        String couponPrevFloat = FieldHelper.getStringFieldValue(fieldValues, PREV_FLT_CPN);
        Boolean sinkable = FieldHelper.getBooleanFromYNString(fieldValues, SINKABLE);
        Boolean isPerpetual = FieldHelper.getBooleanFromYNString(fieldValues, IS_PERPETUAL);


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
