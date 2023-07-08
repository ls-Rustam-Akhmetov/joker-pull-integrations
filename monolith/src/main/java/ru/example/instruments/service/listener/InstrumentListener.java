package ru.example.instruments.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.bcs.perseus.instruments.mapper.CurrencyMeasuresMapper;
import ru.bcs.perseus.instruments.model.CurrencyMeasures;
import ru.bcs.perseus.instruments.model.dto.CurrenciesMeasureUpdateDto;
import ru.bcs.perseus.instruments.model.dto.InstrumentDto;
import ru.bcs.perseus.instruments.model.dto.bond.BondDto;
import ru.bcs.perseus.instruments.model.dto.equity.EquityDto;
import ru.bcs.perseus.instruments.service.CurrencyMeasuresService;
import ru.bcs.perseus.instruments.service.InstrumentsService;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static ru.bcs.perseus.instruments.config.CustomBinding.INSTRUMENTS;
import static ru.bcs.perseus.instruments.config.CustomBinding.INSTRUMENT_MEASURES;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstrumentListener {

  private final InstrumentProcessor instrumentProcessor;
  private final InstrumentsService instrumentsService;
  private final CurrencyMeasuresService currencyMeasuresService;

  private final ObjectMapper objectMapper;

  @StreamListener(target = INSTRUMENTS)
  public void onMessage(@Payload String jsonMessage) {
    InstrumentDto instrument;
    try {
      instrument = objectMapper.readValue(jsonMessage, InstrumentDto.class);

      switch (instrument.getType()) {
        case BOND:
          BondDto bondDto = objectMapper.readValue(jsonMessage, BondDto.class);
          instrumentProcessor.saveBond(bondDto);
          break;
        case EQUITY:
          EquityDto equityDto = objectMapper.readValue(jsonMessage, EquityDto.class);
          instrumentProcessor.saveEquity(equityDto);
          break;
        default:
          log.warn("Instrument with type {} and figi {} is skipped", instrument.getType(),
              instrument.getId());
      }
    } catch (IOException e) {
      log.error("Failed to deserialize Instrument object. AMQP message body: {} ", jsonMessage, e);
    } catch (ConstraintViolationException e) {
      log.warn("Instrument validation failed.AMQP message body: {},\n constraint violations:{}",
          jsonMessage, e.getConstraintViolations());
    }
  }

  @StreamListener(INSTRUMENT_MEASURES)
  public void onMeasuresUpdate(CurrenciesMeasureUpdateDto dto) {
    log.debug("Receive new measures[{}]", dto);
    CurrencyMeasures measures = CurrencyMeasuresMapper.INSTANCE.mapFromDto(dto);
    currencyMeasuresService.save(measures);
    Map<String, BigDecimal> instrumentIdToDividendMap = getInstrumentIdDividendYieldMap(dto);

    instrumentsService.updateDividendYield(instrumentIdToDividendMap);
    instrumentsService.updateHistoryQuality(dto.getInstrumentId(), dto.getHistoryQuality());
  }

  private Map<String, BigDecimal> getInstrumentIdDividendYieldMap(CurrenciesMeasureUpdateDto dto) {
    Map<String, BigDecimal> instrumentIdToDividendMap = new HashMap<>();
    Double dtoDividendYield = dto.getDividendYield();
    String instrumentId = dto.getInstrumentId();
    if (!isNull(dtoDividendYield)) {
      BigDecimal dividendYield = BigDecimal.valueOf(dtoDividendYield);
      instrumentIdToDividendMap.put(instrumentId, dividendYield);
    }
    return instrumentIdToDividendMap;
  }
}
