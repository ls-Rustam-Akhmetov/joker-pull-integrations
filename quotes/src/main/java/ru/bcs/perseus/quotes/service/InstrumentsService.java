package ru.bcs.perseus.quotes.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bcs.perseus.quotes.mapper.InstrumentsMapper;
import ru.bcs.perseus.quotes.model.SimpleInstrument;
import ru.bcs.perseus.quotes.model.dto.instruments.FoundInstrumentsDTO;
import ru.bcs.perseus.quotes.model.dto.instruments.Instrument;
import ru.bcs.perseus.quotes.model.quotes.Exchange;
import ru.bcs.perseus.quotes.repository.InstrumentsRepository;

@Service
@RequiredArgsConstructor
public class InstrumentsService {

  private final InstrumentsRepository repository;
  private final InstrumentsMapper instrumentsMapper;

  public Optional<SimpleInstrument> getSimpleInstrument(String id) {
    Optional<Instrument> optionalInstrument = getInstrument(id);
    return optionalInstrument.flatMap(instrumentsMapper::map);
  }

  public Optional<Instrument> getInstrument(String id) {
    FoundInstrumentsDTO foundInstrumentsDTO = repository.getInstrument(id);

    List<Instrument> instruments = Stream
        .of(foundInstrumentsDTO.getBonds(), foundInstrumentsDTO.getEquities())
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    Optional<Instrument> notOtcInstrument = instruments.stream()
        .filter(instrument -> instrument.getExchange() != Exchange.OTC)
        .findFirst();

    if (notOtcInstrument.isPresent()) {
      return notOtcInstrument;
    } else {
      return instruments.stream().findFirst();
    }
  }
}
