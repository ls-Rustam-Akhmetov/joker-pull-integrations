package ru.bcs.perseus.quotes.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bcs.perseus.quotes.mapper.QuotesMapper;
import ru.bcs.perseus.quotes.model.InstrumentType;
import ru.bcs.perseus.quotes.model.dto.QuoteDto;
import ru.bcs.perseus.quotes.model.dto.instruments.bond.Bond;
import ru.bcs.perseus.quotes.model.quotes.Exchange;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.service.QuotesService;

import java.time.LocalDate;
import java.util.List;


import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
public class QuotesController extends BaseController {

    private final QuotesService service;
    private final QuotesMapper mapper;

    /**
     * used by [partners]
     *
     * @param isins
     * @return
     */
    @ApiOperation(value = "Получение котировок по инструменту",
            notes = "Получение котировок по всем биржам для данного инструмента (в данный момент 'MOEX' и 'OTC')")
    @GetMapping(params = "ids")
    public List<QuoteDto> getQuotes(
            @ApiParam(value = "Равен isin если присутствует у инструмента, в противном случае ticker")
            @RequestParam("ids") List<String> isins) {
        return mapper.mapAllToDto(service.getQuotesByIsins(isins, LocalDate.now()));
    }

    /**
     * used by [portfolio]
     *
     * @param instrumentIds
     * @return
     */
    @ApiOperation(value = "Получение котировок по id инструмента",
            notes = "Получение котировок ID инструмента")
    @GetMapping(params = "instrumentIds")
    public List<QuoteDto> getQuotesByInstrmentIds(
            @ApiParam(value = "ID инструмента. Равен figi или валютной паре")
            @RequestParam("instrumentIds") List<String> instrumentIds) {
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

    /**
     * used by [trades]
     *
     * @param isin
     * @param date
     * @return
     */
    @ApiOperation("Get quote by instrument id and date")
    @GetMapping("{isin}")
    public QuoteDto getQuote(
            @ApiParam(value = "ISIN", required = true)
            @PathVariable("isin") String isin,
            @ApiParam(value = "Quote date", defaultValue = "2018-11-28")
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @ApiParam(value = "search quote in past if not find today")
            @RequestParam(value = "findLatest", defaultValue = "false") boolean findLatest
    ) {

        Quote quote = findLatest ? service.getLastQuote(isin, date) : service.getQuote(isin, date);
        return mapper.mapQuote(quote);
    }
}
