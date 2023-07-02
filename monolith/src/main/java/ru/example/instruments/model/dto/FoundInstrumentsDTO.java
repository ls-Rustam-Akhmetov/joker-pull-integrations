package ru.example.instruments.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.example.instruments.model.dto.bond.BondDto;
import ru.example.instruments.model.dto.equity.EquityDto;

import java.util.List;

@Data
@AllArgsConstructor
public class FoundInstrumentsDTO {

    private List<BondDto> bonds;
    private List<EquityDto> equities;
    private List<InstrumentDto> cash;
}
