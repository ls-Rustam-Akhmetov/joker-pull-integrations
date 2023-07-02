package ru.bcs.perseus.quotes.in.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.bcs.perseus.quotes.mapper.QuotesMapper;
import ru.bcs.perseus.quotes.model.dto.AccruedInterestTradeDateWrapper;
import ru.bcs.perseus.quotes.model.dto.NominalWrapper;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.service.QuotesService;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("support")
@Slf4j
public class SupportQuotesController {

    private final QuotesService quotesService;
    private final QuotesMapper quotesMapper;

    @GetMapping(path = "/byIds")
    public List<Quote> getQuotesByIds(@RequestParam("quoteId") List<String> ids) {
        return quotesService.getQuotes(ids);
    }

    @PostMapping
    public void addQuote(@RequestBody List<Quote> quote) {
        quotesService.save(quote);
    }

    @DeleteMapping("{id}")
    public void deleteQuote(@PathVariable("id") String id) {
        quotesService.deleteQuote(id);
    }

    @DeleteMapping
    public void deleteQuoteBeforeDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        quotesService.deleteQuotesBeforeDate(date);
    }

    @GetMapping("count")
    public AmountWrapper getQuotesAmount() {
        return new AmountWrapper(quotesService.getAmount());
    }

    @GetMapping("date/count")
    public AmountWrapper getQuotesAmountByDate(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return new AmountWrapper(quotesService.getAmountByDate(date));
    }

    @GetMapping("paged")
    public List<Quote> getQuotes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return quotesService.get(page, size);
    }

    @GetMapping("date/paged")
    public List<Quote> getQuotes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return quotesService.get(page, size, date);
    }

    @GetMapping
    public List<QuoteDto> getQuotes(@RequestParam("instrumentId") List<String> instrumentIds) {
        return quotesMapper.mapAllToDto(
                quotesService.getQuotesByInstruments(instrumentIds)
        );
    }

    @PostMapping("/nominal-list")
    public void updateNominalQuote(@RequestBody final List<NominalWrapper> nominalWrapperList) {
        quotesService.updateNominalQuote(nominalWrapperList);
    }

    @PostMapping("/accrued-interest-trade-date")
    public void updateAccruedInterestTradeDate(
            @RequestBody final List<AccruedInterestTradeDateWrapper> accruedInterestTradeDateWrappers) {
        quotesService.updateAccruedInterestTradeDate(accruedInterestTradeDateWrappers);
    }

    public record AmountWrapper(long amount) {
    }
}
