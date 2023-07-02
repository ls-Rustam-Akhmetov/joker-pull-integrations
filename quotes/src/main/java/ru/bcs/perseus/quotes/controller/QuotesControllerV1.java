package ru.bcs.perseus.quotes.controller;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bcs.perseus.quotes.mapper.QuotesMapper;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.quotes.Exchange;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.service.QuotesService;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/quotes")
public class QuotesControllerV1 {

  private final QuotesMapper mapper;
  private final QuotesService quotesService;

  @ApiOperation(value = "method to get quotes", notes = "instrumentId is not unique identifier, if date is not set, then get today quotes")
  @GetMapping("search")
  public List<QuoteDto> get(
      @RequestParam(value = "instrumentId", required = false) String instrumentId,
      @RequestParam(value = "isin", required = false) String isin,
      @RequestParam(value = "exchange", required = false) Exchange exchange,
      @ApiParam(value = "Quote date", defaultValue = "2018-11-28")
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

  @ApiOperation(value = "get Quotes by isin", notes = "if date is not set, then get today quotes")
  @GetMapping
  public List<QuoteDto> getQuotes(
      @ApiParam(defaultValue = "RU000A0JNPK5")
      @RequestParam("isin") List<String> isins,
      @ApiParam(value = "Quote date", defaultValue = "2018-11-28")
      @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
  ) {
    return mapper.mapAllToDto(
        quotesService.getQuotesByIsins(isins, firstNonNull(date, LocalDate.now()))
    );
  }
}
