package ru.example.instruments.out.ws.helper;

import lombok.extern.slf4j.Slf4j;
import ru.example.instruments.model.Constant;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static ru.example.instruments.model.Constant.InstrumentFields.*;

@Slf4j
public class InstrumentParser {

    private InstrumentParser() {
    }

    public static Instrument createInstrument(Map<String, String> fieldValues, InstrumentType type) {

        LocalDate maturity = FieldParser.getLocalDateFieldValue(fieldValues, MATURITY);
        String securityDes = FieldParser.getStringFieldValue(fieldValues, SECURITY_DES);
        String figi = FieldParser.getStringFieldValue(fieldValues, FIGI);
        String blExchange = FieldParser.getStringFieldValue(fieldValues, EXCHANGE);
        String marketSector = FieldParser.getStringFieldValue(fieldValues, MARKET_SECTOR_DES);
        Integer marketSectorId = FieldParser.getIntFieldValue(fieldValues, MARKET_SECTOR);
        String cfiCode = FieldParser.getStringFieldValue(fieldValues, CFI_CODE);
        String bloombergCfiCode = FieldParser.getStringFieldValue(fieldValues, BLOOMBERG_CFI_CODE);
        String countryIso = FieldParser.getStringFieldValue(fieldValues, COUNTRY_ISO);
        String countryDomicile = FieldParser.getStringFieldValue(fieldValues, CNTRY_OF_DOMICILE);
        BigDecimal tradeLotSize = FieldParser.getBigDecimalFieldValue(fieldValues, PX_TRADE_LOT_SIZE);
        BigDecimal quoteLotSize = FieldParser.getBigDecimalFieldValue(fieldValues, PX_QUOTE_LOT_SIZE);
        BigDecimal roundLotSize = FieldParser.getBigDecimalFieldValue(fieldValues, PX_ROUND_LOT_SIZE);
        String currencyQuoteFactor = FieldParser.getStringFieldValue(fieldValues, QUOTE_FACTOR);

        if (blExchange == null) {
            log.warn("Not created instrument from response field {} because field EXCHANGE is null",
                    fieldValues);
            return null;
        }

        return Instrument.builder()
                .id(figi)
                .blExchange(blExchange)
                .lastModified(LocalDateTime.now())
                .endOfUseDate(maturity)
                .user(Constant.USERNAME)
                .ticker(securityDes)
                .source(Constant.SOURCE)
                .type(type)
                .marketSector(marketSector)
                .marketSectorId(marketSectorId)
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
