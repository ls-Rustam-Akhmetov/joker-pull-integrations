package ru.example.instruments.service.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bcs.perseus.instruments.mapper.InstrumentMapper;
import ru.bcs.perseus.instruments.model.bond.Bond;
import ru.bcs.perseus.instruments.model.dto.bond.BondDto;
import ru.bcs.perseus.instruments.model.dto.equity.EquityDto;
import ru.bcs.perseus.instruments.model.equity.Equity;
import ru.bcs.perseus.instruments.service.InstrumentsService;

import javax.validation.Valid;

@Service
@Validated
@RequiredArgsConstructor
public class InstrumentProcessor {

  private final InstrumentsService instrumentsService;

  public void saveEquity(@Valid EquityDto equityDto) {
    Equity equity = InstrumentMapper.INSTANCE.map(equityDto);
    instrumentsService.upsertInstrument(equity);
  }

  public void saveBond(@Valid BondDto bondDto) {
    Bond bond = InstrumentMapper.INSTANCE.map(bondDto);
    instrumentsService.upsertInstrument(bond);
  }
}
