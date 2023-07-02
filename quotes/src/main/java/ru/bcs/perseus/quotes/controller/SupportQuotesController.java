package ru.bcs.perseus.quotes.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.bcs.perseus.quotes.mapper.QuotesMapper;
import ru.bcs.perseus.quotes.model.dto.AccruedInterestTradeDateWrapper;
import ru.bcs.perseus.quotes.model.dto.NominalWrapper;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.service.CsvParser;
import ru.bcs.perseus.quotes.service.QuotesService;

@RestController
@AllArgsConstructor
@RequestMapping("support")
@Slf4j
public class SupportQuotesController extends BaseController {

  private final QuotesService quotesService;
  private final QuotesMapper quotesMapper;
  private final CsvParser csvParser;

  @ApiOperation("Get quotes by ids")
  @GetMapping(path = "/byIds")
  public List<Quote> getQuotesByIds(@RequestParam("quoteId") List<String> ids) {
    return quotesService.getQuotes(ids);
  }

  @ApiOperation("Add new quotes")
  @PostMapping
  public void addQuote(@RequestBody List<Quote> quote) {
    quotesService.save(quote);
  }

  @ApiOperation("delete quote")
  @DeleteMapping("{id}")
  public void deleteQuote(@PathVariable("id") String id) {
    quotesService.deleteQuote(id);
  }

  @ApiOperation("Delete all quotes before date")
  @DeleteMapping
  public void deleteQuoteBeforeDate(
      @ApiParam("Date to delete before (exclude date). ISO Date Format {yyyy-MM-dd}")
      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
  ) {
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

  @DeleteMapping("all")
  public void deleteAll() {
    quotesService.deleteAll();
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

  //FIXME remove method after release 1.25.0
  @PostMapping("/csv")
  public void upload(@RequestParam final MultipartFile file) throws IOException {
    csvParser.processQuotes(file.getInputStream());
  }

  @Data
  public static class AmountWrapper {

    private final long amount;
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
}
