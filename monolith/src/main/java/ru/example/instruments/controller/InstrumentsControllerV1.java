package ru.example.instruments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.instruments.mapper.InstrumentMapper;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.dto.FoundInstrumentsDTO;
import ru.example.instruments.model.dto.InstrumentDto;
import ru.example.instruments.model.dto.InstrumentTypeDto;
import ru.example.instruments.model.exception.NotFoundException;
import ru.example.instruments.service.InstrumentsService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instruments")
public class InstrumentsControllerV1 {

    private final InstrumentsService instrumentsService;

    @GetMapping
    public FoundInstrumentsDTO getInstrument(@RequestParam(value = "ids") List<String> ids) {
        return InstrumentMapper.INSTANCE.map(instrumentsService.getInstruments(ids));
    }

    @GetMapping("/filter")
    public List<InstrumentDto> findInstruments(@RequestParam(value = "isin") String isin,
                                               @RequestParam(value = "exchange", required = false) Exchange exchange) {
        List<Instrument> instruments = instrumentsService.findByIsin(isin);
        boolean isBond = instruments.stream()
                .map(Instrument::getType).anyMatch(type -> type == InstrumentType.BOND);

        if (exchange != null && !isBond) {
            instruments = instruments.stream()
                    .filter(instrument -> exchange.equals(instrument.getExchange()))
                    .collect(toList());
        }

        return instruments.stream()
                .map(InstrumentMapper.INSTANCE::map)
                .collect(toList());
    }

    @GetMapping("/search")
    public List<InstrumentDto> searchInstruments(@RequestParam String query,
                                                 @RequestParam(required = false) InstrumentType type) {
        final List<Instrument> instruments = instrumentsService
                .searchInstrument(query.toUpperCase(), type);
        return instruments.stream()
                .map(InstrumentMapper.INSTANCE::map)
                .collect(toList());
    }

    @GetMapping("/{instrumentId}/type")
    public InstrumentTypeDto getInstrumentType(@PathVariable("instrumentId") String id) {

        InstrumentType instrumentType = instrumentsService
                .findInstruments(List.of(id))
                .stream()
                .map(Instrument::getType)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Instrument with instrumentId " + id + " not found"));

        return new InstrumentTypeDto(instrumentType);
    }

    @GetMapping("by-exchange")
    public Set<String> getByExchangeCode(@RequestParam("code") List<Exchange> exchanges) {
        Set<String> bondIsins = instrumentsService.getBondIsins();
        Set<String> resultIsins = exchanges.stream()
                .flatMap(exchange -> instrumentsService.getIsinsByExchange(exchange).stream())
                .collect(Collectors.toSet());

        resultIsins.addAll(bondIsins);
        return resultIsins;
    }

    @GetMapping(value = "paged", produces = APPLICATION_JSON_VALUE)
    public FoundInstrumentsDTO getInstrument(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "types", defaultValue = "BOND, EQUITY") List<InstrumentType> types
    ) {
        final List<Instrument> foundInstruments = instrumentsService.get(page, size, types);
        return InstrumentMapper.INSTANCE.map(foundInstruments);
    }
}
