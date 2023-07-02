package ru.bcs.perseus.quotes.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bcs.perseus.quotes.mapper.QuotesMapper;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.service.QuotesService;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2.0/quotes")
public class QuotesControllerV2 {

  private final QuotesMapper mapper;
  private final QuotesService quotesService;

  @ApiOperation(value = "get Quotes by instrumentId or by one instrumentId and Date range", notes = "date range search is inclusive")
  @GetMapping
  public List<QuoteDto> getQuotes(
      @RequestParam("instrumentId") List<String> instrumentIds,
      @ApiParam(defaultValue = "2018-10-10")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      @RequestParam(value = "startDate", required = false) LocalDate startDate,
      @ApiParam(defaultValue = "2018-10-15")
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
