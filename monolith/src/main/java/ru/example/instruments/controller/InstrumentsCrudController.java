package ru.example.instruments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.example.instruments.mapper.InstrumentMapper;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.dto.InstrumentDto;
import ru.example.instruments.model.dto.bond.BondDto;
import ru.example.instruments.model.dto.equity.EquityDto;
import ru.example.instruments.service.InstrumentsService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("support")
@Validated
public class InstrumentsCrudController {

    private final InstrumentsService service;

    @GetMapping("/instruments/count")
    public long getInstrumentCount(@RequestParam(value = "type", required = false) InstrumentType type) {
        if (type == null) {
            return service.getInstrumentsCount();
        } else {
            return service.getCount(type);
        }
    }

    @GetMapping("instruments")
    public List<InstrumentDto> get(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "types", defaultValue = "BOND, EQUITY") List<InstrumentType> types
    ) {
        final List<Instrument> instruments = service.get(page, size, types);

        return instruments.stream()
                .map(InstrumentMapper.INSTANCE::map)
                .collect(Collectors.toList());
    }

    @PostMapping("/bonds")
    public void saveBonds(@RequestBody @Valid List<BondDto> bondsDto) {
        bondsDto.stream()
                .map(InstrumentMapper.INSTANCE::map)
                .forEach(service::save);
    }

    @PostMapping("/equities")
    public void saveEquities(@RequestBody @Valid List<EquityDto> equitiesDto) {
        equitiesDto.stream()
                .map(InstrumentMapper.INSTANCE::map)
                .forEach(service::save);
    }

    @PostMapping
    public void save(@RequestBody @Valid List<InstrumentDto> instrumentsDtos) {
        instrumentsDtos.stream()
                .map(InstrumentMapper.INSTANCE::map)
                .forEach(service::save);
    }
}
