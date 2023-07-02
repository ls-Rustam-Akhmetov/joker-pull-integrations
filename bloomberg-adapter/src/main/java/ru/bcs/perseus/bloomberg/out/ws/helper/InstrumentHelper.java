package ru.bcs.perseus.bloomberg.out.ws.helper;

import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.BLOOMBERG_CFI_CODE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CFI_CODE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CNTRY_OF_DOMICILE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.COMPOSITE_FIGI;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.COUNTRY_ISO;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.CRNCY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.EXCHANGE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.FIGI;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MARKET_SECTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MARKET_SECTOR_DES;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.MATURITY;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PX_QUOTE_LOT_SIZE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PX_ROUND_LOT_SIZE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.PX_TRADE_LOT_SIZE;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.QUOTE_FACTOR;
import static ru.bcs.perseus.bloomberg.model.Constant.InstrumentFields.SECURITY_DES;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getBigDecimalFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getIntFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getLocalDateFieldValue;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getStringFieldValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import ru.bcs.perseus.bloomberg.model.Constant;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentType;

@Slf4j
public class InstrumentHelper {

  private InstrumentHelper() {
  }

  public static Instrument createInstrument(Map<String, String> fieldValues, InstrumentType type) {

    LocalDate maturity = getLocalDateFieldValue(fieldValues, MATURITY);
    String securityDes = getStringFieldValue(fieldValues, SECURITY_DES);
    String figi = getStringFieldValue(fieldValues, FIGI);
    String compositeFigi = getStringFieldValue(fieldValues, COMPOSITE_FIGI);
    String blExchange = getStringFieldValue(fieldValues, EXCHANGE);
    String marketSector = getStringFieldValue(fieldValues, MARKET_SECTOR_DES);
    Integer marketSectorId = getIntFieldValue(fieldValues, MARKET_SECTOR);
    String currency = getStringFieldValue(fieldValues, CRNCY);
    String cfiCode = getStringFieldValue(fieldValues, CFI_CODE);
    String bloombergCfiCode = getStringFieldValue(fieldValues, BLOOMBERG_CFI_CODE);
    String countryIso = getStringFieldValue(fieldValues, COUNTRY_ISO);
    String countryDomicile = getStringFieldValue(fieldValues, CNTRY_OF_DOMICILE);
    BigDecimal tradeLotSize = getBigDecimalFieldValue(fieldValues, PX_TRADE_LOT_SIZE);
    BigDecimal quoteLotSize = getBigDecimalFieldValue(fieldValues, PX_QUOTE_LOT_SIZE);
    BigDecimal roundLotSize = getBigDecimalFieldValue(fieldValues, PX_ROUND_LOT_SIZE);
    String currencyQuoteFactor = getStringFieldValue(fieldValues, QUOTE_FACTOR);

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
