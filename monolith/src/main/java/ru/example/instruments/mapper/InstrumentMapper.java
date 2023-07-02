package ru.example.instruments.mapper;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.bond.Bond;
import ru.example.instruments.model.dto.FoundInstrumentsDTO;
import ru.example.instruments.model.dto.InstrumentDto;
import ru.example.instruments.model.dto.bond.BondDto;
import ru.example.instruments.model.dto.equity.EquityDto;
import ru.example.instruments.model.equity.Equity;
import ru.example.instruments.util.InstrumentUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Slf4j
@Mapper(unmappedTargetPolicy = IGNORE)
public abstract class InstrumentMapper {

    public static final InstrumentMapper INSTANCE = Mappers.getMapper(InstrumentMapper.class);

    public InstrumentDto map(Instrument instrument) {
        if (instrument == null) {
            return null;
        }
        return switch (instrument.getType()) {
            case EQUITY -> map((Equity) instrument);
            case BOND -> map((Bond) instrument);
            case CURRENCY -> mapCurrency(instrument);
            default -> null;
        };
    }

    @Mapping(target = "id", source = "equity.figi")
    @Mapping(target = "currency", source = "equity.staticData.currency")
    public abstract EquityDto map(Equity equity);

    @Mapping(target = "id", source = "bond.figi")
    @Mapping(target = "currency", source = "bond.staticData.currency")
    public abstract BondDto map(Bond bond);

    public abstract InstrumentDto mapCurrency(Instrument instrument);

    @Mapping(target = "figi", source = "id")
    public abstract Instrument map(InstrumentDto instrumentDto);

    @Mapping(target = "figi", source = "id")
    @Mapping(target = "id", ignore = true)
    public abstract Equity map(EquityDto equity);

    @Mapping(target = "figi", source = "id")
    @Mapping(target = "id", ignore = true)
    public abstract Bond map(BondDto bond);

    @SuppressWarnings("Duplicates")
    public FoundInstrumentsDTO map(List<Instrument> foundInstruments) {

        List<Bond> bonds = InstrumentUtils.getBonds(foundInstruments);
        List<Equity> equities = InstrumentUtils.getEquities(foundInstruments);
        List<Instrument> cash = InstrumentUtils.getCash(foundInstruments);

        List<BondDto> bondDtoList = bonds
                .stream()
                .map(this::map)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<EquityDto> equityDtoList = equities
                .stream()
                .map(this::map)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<InstrumentDto> cashDtoList = cash
                .stream()
                .map(this::map)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new FoundInstrumentsDTO(bondDtoList, equityDtoList, cashDtoList);
    }
}
