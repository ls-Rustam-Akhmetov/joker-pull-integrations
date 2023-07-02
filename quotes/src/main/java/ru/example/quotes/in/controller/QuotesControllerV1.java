package ru.example.quotes.in.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.example.quotes.mapper.QuotesMapper;
import ru.example.quotes.model.InstrumentType;
import ru.example.quotes.model.dto.QuoteDto;
import ru.example.quotes.model.quotes.Exchange;
import ru.example.quotes.model.quotes.Quote;
import ru.example.quotes.service.QuotesService;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/quotes")
public class QuotesControllerV1 {

    private final QuotesService service;
    private final QuotesMapper mapper;

    @GetMapping(params = "ids")
    public List<QuoteDto> getQuotes(@RequestParam("ids") List<String> isins) {
        return mapper.mapAllToDto(service.getQuotesByIsins(isins, LocalDate.now()));
    }

    @GetMapping(params = "instrumentIds")
    public List<QuoteDto> getQuotesByInstrmentIds(@RequestParam("instrumentIds") List<String> instrumentIds) {
        return mapper.mapAllToDto(service.getQuotesByInstruments(instrumentIds));
    }

    @GetMapping(params = "top")
    public List<QuoteDto> getLastQuotes(
            @RequestParam("isin") String instrumentId,
            @RequestParam(value = "exchange", required = false, defaultValue = "OTC") Exchange exchange,
            @RequestParam(value = "top") Integer top
    ) {
        final PageRequest pageRequest = PageRequest.of(0, top * 2);
        List<Quote> dbQuotes = service.getLastQuotes(instrumentId, pageRequest);
        boolean isBondQuote = dbQuotes.stream().map(Quote::getType).allMatch(type -> type == InstrumentType.BOND);
        if (!isBondQuote) {
            dbQuotes = dbQuotes.stream()
                    .filter(quote -> exchange.equals(quote.getExchange()))
                    .limit(top)
                    .collect(toList());
        }

        return mapper.mapAllToDto(dbQuotes);
    }

    @GetMapping("{isin}")
    public QuoteDto getQuote(
            @PathVariable("isin") String isin,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "findLatest", defaultValue = "false") boolean findLatest
    ) {
        Quote quote = findLatest ? service.getLastQuote(isin, date) : service.getQuote(isin, date);
        return mapper.mapQuote(quote);
    }
}
