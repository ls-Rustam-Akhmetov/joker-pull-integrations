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
import static ru.example.bloomberg.out.ws.helper.CalculationUtils.getAccruedInterest;

public class BondParser {

    private BondParser() {
    }

    public static Bond createBond(Map<String, String> fieldValues) {
        Instrument instrument = InstrumentParser.createInstrument(fieldValues, InstrumentType.BOND);
        if (instrument == null) {
            return null;
        }

        BondStatic bondStatic = getBondStatic(fieldValues);
        Rating rating = RatingParser.getRating(fieldValues);

        return new Bond(instrument, bondStatic, rating);
    }

    private static BondStatic getBondStatic(Map<String, String> fieldValues) {

        String isin = FieldParser.getStringFieldValue(fieldValues, ID_ISIN);
        String name = FieldParser.getStringFieldValue(fieldValues, NAME);
        String currency = FieldParser.getStringFieldValue(fieldValues, CRNCY);
        BigDecimal nominal = FieldParser.getBigDecimalFieldValue(fieldValues, PAR_AMT);
        LocalDate issueDate = FieldParser.getLocalDateFieldValue(fieldValues, ANNOUNCE_DT);
        Long issuedAmount = FieldParser.getLongFieldValue(fieldValues, AMT_ISSUED);
        String paymentRank = FieldParser.getStringFieldValue(fieldValues, PAYMENT_RANK);
        String industrySector = FieldParser.getStringFieldValue(fieldValues, INDUSTRY_SECTOR);
        Boolean putable = FieldParser.getBooleanFromYNString(fieldValues, PUTABLE);
        Boolean callable = FieldParser.getBooleanFromYNString(fieldValues, CALLABLE);
        LocalDate nextPutDate = FieldParser.getLocalDateFieldValue(fieldValues, NXT_PUT_DT);
        LocalDate nextCallDate = FieldParser.getLocalDateFieldValue(fieldValues, NXT_CALL_DT);
        BigDecimal coupon = FieldParser.getBigDecimalFieldValue(fieldValues, CPN);
        LocalDate couponNextDate = FieldParser.getLocalDateFieldValue(fieldValues, NXT_CPN_DT);
        LocalDate couponPrevDate = FieldParser.getLocalDateFieldValue(fieldValues, PREV_CPN_DT);
        BigDecimal couponFrequency = FieldParser.getBigDecimalFieldValue(fieldValues, CPN_FREQ);
        LocalDate maturity = FieldParser.getLocalDateFieldValue(fieldValues, MATURITY);
        BigDecimal duration = FieldParser.getBigDecimalFieldValue(fieldValues, DUR_ADJ_MID);
        LocalDate settlementDate = FieldParser.getLocalDateFieldValue(fieldValues, SETTLE_DT);
        BigDecimal intAcc = FieldParser.getBigDecimalFieldValue(fieldValues, INT_ACC);
        BigDecimal minimalLot = FieldParser.getBigDecimalFieldValue(fieldValues, MIN_PIECE);
        BigDecimal accruedInterest = getAccruedInterest(nominal, intAcc);
        BigDecimal assetSwapSpread = FieldParser.getBigDecimalFieldValue(fieldValues, YAS_ASW_SPREAD);
        BigDecimal marketCapitalization = FieldParser.getBigDecimalFieldValue(fieldValues, CUR_MKT_CAP);
        Long issuerId = FieldParser.getLongFieldValue(fieldValues, ID_BB_COMPANY);
        String securityName = FieldParser.getStringFieldValue(fieldValues, SECURITY_NAME);
        LocalDate finalMaturity = FieldParser.getLocalDateFieldValue(fieldValues, FINAL_MATURITY);
        BigDecimal zSpreadAsk = FieldParser.getBigDecimalFieldValue(fieldValues, Z_SPRD_ASK);
        BigDecimal outstandingAmount = FieldParser.getBigDecimalFieldValue(fieldValues, AMT_OUTSTANDING);
        BigDecimal macaulayDuration = FieldParser.getBigDecimalFieldValue(fieldValues, DUR_MID);
        String industryGroup = FieldParser.getStringFieldValue(fieldValues, INDUSTRY_GROUP);
        String dayCountConvention = FieldParser.getStringFieldValue(fieldValues, DAY_CNT_DES);
        String shortDescription = FieldParser.getStringFieldValue(fieldValues, SECURITY_SHORT_DES);
        Boolean ruLombardList = FieldParser.getBooleanFromYNString(fieldValues, RUSSIAN_CB_LOMBARD);
        String issuerCountry = FieldParser.getStringFieldValue(fieldValues, COUNTRY);
        String couponType = FieldParser.getStringFieldValue(fieldValues, CPN_TYP);
        BigDecimal durationAdjOasMid = FieldParser.getBigDecimalFieldValue(fieldValues, DUR_ADJ_OAS_MID);
        BigDecimal durationAdjMtyMid = FieldParser.getBigDecimalFieldValue(fieldValues, DUR_ADJ_MTY_MID);
        BigDecimal oasSpread = FieldParser.getBigDecimalFieldValue(fieldValues, OAS_SPREAD_DUR_MID);
        BigDecimal durationSpread = FieldParser.getBigDecimalFieldValue(fieldValues, SPREAD_DUR_ADJ_ASK);
        Boolean convertible = FieldParser.getBooleanFromYNString(fieldValues, CONVERTIBLE);
        BigDecimal minimalOrderIncrement = FieldParser.getBigDecimalFieldValue(fieldValues, MIN_INCREMENT);
        String couponPrevFloat = FieldParser.getStringFieldValue(fieldValues, PREV_FLT_CPN);
        Boolean sinkable = FieldParser.getBooleanFromYNString(fieldValues, SINKABLE);
        Boolean isPerpetual = FieldParser.getBooleanFromYNString(fieldValues, IS_PERPETUAL);


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
