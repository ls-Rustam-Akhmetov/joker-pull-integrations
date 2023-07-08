package ru.example.instruments.in;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.example.instruments.mapper.InstrumentMapper;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.dto.FoundInstrumentsDTO;
import ru.example.instruments.service.InstrumentsService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/instruments")
public class InstrumentsControllerV2 {

    private final InstrumentsService instrumentsService;

    @GetMapping
    public FoundInstrumentsDTO get(@RequestParam(value = "isin", required = false) List<String> isins) {
        final List<Instrument> foundInstruments = instrumentsService.findByIsins(isins);
        return InstrumentMapper.INSTANCE.map(foundInstruments);
    }

    @GetMapping("search")
    public FoundInstrumentsDTO getInstrument(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "type", required = false) InstrumentType type,
            @RequestParam(value = "exchange", required = false) Exchange exchange,
            @RequestParam(value = "ticker", required = false) String ticker,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        Instrument instrument = Instrument.builder()
                .figi(id)
                .type(type)
                .exchange(exchange)
                .ticker(ticker)
                .build();

        final List<Instrument> foundInstruments = instrumentsService
                .find(instrument, PageRequest.of(page, size));
        return InstrumentMapper.INSTANCE.map(foundInstruments);
    }

    @GetMapping("exchanges")
    public List<Exchange> getExchanges() {
        return Arrays.stream(Exchange.values())
                .collect(Collectors.toList());
    }

    @GetMapping("types")
    public List<InstrumentType> getTypes() {
        return Arrays.stream(InstrumentType.values())
                .collect(Collectors.toList());
    }
}