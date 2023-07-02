package ru.example.bloomberg.out.ws.helper;

import lombok.extern.slf4j.Slf4j;
import ru.example.bloomberg.model.Constant;
import ru.example.bloomberg.model.instrument.Instrument;
import ru.example.bloomberg.model.instrument.InstrumentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static ru.example.bloomberg.model.Constant.InstrumentFields.*;

@Slf4j
public class InstrumentHelper {

    private InstrumentHelper() {
    }

    public static Instrument createInstrument(Map<String, String> fieldValues, InstrumentType type) {

        LocalDate maturity = FieldHelper.getLocalDateFieldValue(fieldValues, MATURITY);
        String securityDes = FieldHelper.getStringFieldValue(fieldValues, SECURITY_DES);
        String figi = FieldHelper.getStringFieldValue(fieldValues, FIGI);
        String compositeFigi = FieldHelper.getStringFieldValue(fieldValues, COMPOSITE_FIGI);
        String blExchange = FieldHelper.getStringFieldValue(fieldValues, EXCHANGE);
        String marketSector = FieldHelper.getStringFieldValue(fieldValues, MARKET_SECTOR_DES);
        Integer marketSectorId = FieldHelper.getIntFieldValue(fieldValues, MARKET_SECTOR);
        String currency = FieldHelper.getStringFieldValue(fieldValues, CRNCY);
        String cfiCode = FieldHelper.getStringFieldValue(fieldValues, CFI_CODE);
        String bloombergCfiCode = FieldHelper.getStringFieldValue(fieldValues, BLOOMBERG_CFI_CODE);
        String countryIso = FieldHelper.getStringFieldValue(fieldValues, COUNTRY_ISO);
        String countryDomicile = FieldHelper.getStringFieldValue(fieldValues, CNTRY_OF_DOMICILE);
        BigDecimal tradeLotSize = FieldHelper.getBigDecimalFieldValue(fieldValues, PX_TRADE_LOT_SIZE);
        BigDecimal quoteLotSize = FieldHelper.getBigDecimalFieldValue(fieldValues, PX_QUOTE_LOT_SIZE);
        BigDecimal roundLotSize = FieldHelper.getBigDecimalFieldValue(fieldValues, PX_ROUND_LOT_SIZE);
        String currencyQuoteFactor = FieldHelper.getStringFieldValue(fieldValues, QUOTE_FACTOR);

        if (blExchange == null) {
            log.warn("Not created instrument from response field {} because field EXCHANGE is null",
                    fieldValues);
            return null;
        }

        return Instrument.builder()
                .id(figi)
                .blExchange(blExchange)
                .compositeFigi(compositeFigi)
                .lastModified(LocalDateTime.now())
                .endOfUseDate(maturity)
                .user(Constant.USERNAME)
                .ticker(securityDes)
                .source(Constant.SOURCE)
                .type(type)
                .marketSector(marketSector)
                .marketSectorId(marketSectorId)
                .currency(currency)
                .cfiCode(cfiCode)
                .bloombergCfiCode(bloombergCfiCode)
                .countryIso(countryIso)
                .countryDomicile(countryDomicile)
                .tradeLotSize(tradeLotSize)
                .quoteLotSize(quoteLotSize)
                .roundLotSize(roundLotSize)
                .currencyQuoteFactor(currencyQuoteFactor)
                .build();
    }
}
