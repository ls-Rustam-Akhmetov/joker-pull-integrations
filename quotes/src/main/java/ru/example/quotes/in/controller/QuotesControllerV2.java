package ru.example.quotes.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.example.quotes.mapper.QuotesMapper;
import ru.example.quotes.model.dto.QuoteDto;
import ru.example.quotes.model.quotes.Quote;
import ru.example.quotes.service.QuotesService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/quotes")
public class QuotesControllerV2 {

    private final QuotesMapper mapper;
    private final QuotesService quotesService;

    @GetMapping
    public List<QuoteDto> getQuotes(
            @RequestParam("instrumentId") List<String> instrumentIds,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        boolean dateRangeNotNull = startDate != null && endDate != null;
        boolean canFindHistory = instrumentIds.size() == 1 && dateRangeNotNull;

        List<Quote> quotes = canFindHistory ?
                quotesService.getHistoryQuotes(instrumentIds.get(0), startDate, endDate) :
                quotesService.getLastQuotes(instrumentIds);

        return mapper.mapAllToDto(quotes);
    }

    @GetMapping("/all")
    public List<QuoteDto> getAllQuotes(@RequestParam final String instrumentId) {
        return mapper.mapAllToDto(quotesService.getHistoryQuotes(instrumentId));
    }
}
