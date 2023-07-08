package ru.example.instruments.service.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import ru.bcs.perseus.instruments.mapper.CurrencyMeasuresMapper;
import ru.bcs.perseus.instruments.mapper.Quote;
import ru.bcs.perseus.instruments.mapper.QuoteMapper;
import ru.bcs.perseus.instruments.model.Currency;
import ru.bcs.perseus.instruments.model.CurrencyMeasures;
import ru.bcs.perseus.instruments.model.dto.CurrenciesMeasureUpdateDto;
import ru.bcs.perseus.instruments.model.dto.FlatInstrumentDto;
import ru.bcs.perseus.instruments.model.dto.QuoteDto;
import ru.bcs.perseus.instruments.service.InstrumentsServiceV2;

import java.util.Map;

import static java.lang.String.format;
import static ru.bcs.perseus.instruments.config.CustomBinding.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlatInstrumentListener {

  private final QuoteMapper quoteMapper;
  private final InstrumentsServiceV2 instrumentsServiceV2;
  private final ObjectMapper objectMapper;

  @StreamListener(INSTRUMENTS_V2)
  public void onInstrumentUpdated(FlatInstrumentDto instrumentDto) {
    log.debug("Received FlatInstrument: {}", instrumentDto);

    String instrumentId = instrumentDto.getId();
    Map<String, ?> fields = instrumentDto.getFields();

    instrumentsServiceV2.save(instrumentId, fields);
  }

  @StreamListener(QUOTES)
  public void onQuoteUpdated(QuoteDto quoteDto) {
    String instrumentId = quoteDto.getInstrumentId();
    Quote quote = quoteMapper.fromDto(quoteDto);
    Map<String, Object> fields = getFields(quote);

    instrumentsServiceV2.save(instrumentId, fields);
  }

  @StreamListener(INSTRUMENT_MEASURES_FLAT)
  public void onMeasuresUpdate(CurrenciesMeasureUpdateDto dto) {
    log.debug("Received measures: {}", dto);

    CurrencyMeasures measures = CurrencyMeasuresMapper.INSTANCE.mapFromDto(dto);
    String instrumentId = dto.getInstrumentId();

    Currency currency = instrumentsServiceV2.getCurrency(instrumentId)
        .orElseThrow(() -> new RuntimeException(
            format("Unknown currency for instrument %s", instrumentId)));

    measures.getMeasure(currency)
        .map(this::getFields)
        .ifPresent(fields -> instrumentsServiceV2.save(instrumentId, fields));
  }

  private Map<String, Object> getFields(Object obj) {
    return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
    });
  }
}
