package ru.bcs.perseus.quotes.repository.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bcs.perseus.quotes.model.dto.instruments.FoundInstrumentsDTO;
import ru.bcs.perseus.quotes.repository.InstrumentsRepository;

@Slf4j
@Component
public class InstrumentsRepositoryFallback implements InstrumentsRepository {

  @Override
  public FoundInstrumentsDTO getInstrument(String id) {
    log.warn("Fallback for InstrumentsRepositoryFallback.getSimpleInstrument({})", id);
    return new FoundInstrumentsDTO();
  }
}
