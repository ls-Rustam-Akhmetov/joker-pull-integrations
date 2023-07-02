package ru.bcs.perseus.quotes.mapper;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.bcs.perseus.quotes.model.SimpleInstrument;
import ru.bcs.perseus.quotes.model.dto.instruments.Instrument;
import ru.bcs.perseus.quotes.model.dto.instruments.bond.Bond;
import ru.bcs.perseus.quotes.model.dto.instruments.equity.Equity;


@Mapper(componentModel = "spring")
public interface InstrumentsMapper {

  @Mapping(target = "isin", source = "instrument.staticData.isin")
  @Mapping(target = "currency", source = "instrument.staticData.currency")
  SimpleInstrument map(Equity instrument);

  @Mapping(target = "isin", source = "instrument.staticData.isin")
  @Mapping(target = "currency", source = "instrument.staticData.currency")
  SimpleInstrument map(Bond instrument);


  default Optional<SimpleInstrument> map(Instrument instrument) {
    switch (instrument.getType()) {
      case BOND:
        return Optional.ofNullable(
            map((Bond) instrument)
        );
      case EQUITY:
        return Optional.ofNullable(
            map((Equity) instrument)
        );
      default:
        return Optional.empty();
    }
  }

}
