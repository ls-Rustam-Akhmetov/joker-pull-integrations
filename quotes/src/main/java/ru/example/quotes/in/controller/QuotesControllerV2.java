package ru.example.quotes.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.example.quotes.mapper.QuotesMapper;
import ru.example.quotes.model.dto.QuoteDto;
import ru.example.quotes.model.quotes.Exchange;
import ru.example.quotes.model.quotes.Quote;
import ru.example.quotes.service.QuotesService;

import java.time.LocalDate;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/quotes")
public class QuotesControllerV2 {

    private final QuotesMapper mapper;
    private final QuotesService quotesService;

    @GetMapping("search")
    public List<QuoteDto> get(
            @RequestParam(value = "instrumentId", required = false) String instrumentId,
            @RequestParam(value = "isin", required = false) String isin,
            @RequestParam(value = "exchange", required = false) Exchange exchange,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        Quote quote = Quote.builder()
                .instrumentId(instrumentId)
                .isin(isin)
                .exchange(exchange)
                .date(firstNonNull(date, LocalDate.now()))
                .build();
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Quote> quotes = quotesService.find(quote, pageRequest);

        return mapper.mapAllToDto(quotes);
    }

    @GetMapping
    public List<QuoteDto> getQuotes(
            @RequestParam("isin") List<String> isins,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return mapper.mapAllToDto(
                quotesService.getQuotesByIsins(isins, firstNonNull(date, LocalDate.now()))
        );
    }
}
