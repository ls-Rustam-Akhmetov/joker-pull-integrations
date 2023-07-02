package ru.bcs.perseus.quotes.service;

import static java.time.DayOfWeek.SATURDAY;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.bcs.perseus.quotes.model.InstrumentType;
import ru.bcs.perseus.quotes.model.SimpleInstrument;
import ru.bcs.perseus.quotes.model.quotes.Exchange;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.util.CsvParserUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvParser {

  private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.withDelimiter(';').withHeader();
  private static final String CASH_CURRENCY = "CROSS-RATE";
  private static final int DIVIDEND_ACCURACY = 8;

  private final InstrumentsService instrumentsService;
  private final QuotesService quotesService;

  @Async
  public void processQuotes(final InputStream inputStream) throws IOException {

    try (final Reader reader = new InputStreamReader(inputStream);
        final CSVParser parser = new CSVParser(reader, CSV_FORMAT)) {
      log.info("Started parse csv");
      processQuotes(parser);
      log.info("End parsing");
    } catch (Exception e) {
      log.error("Exception ", e);
    }
  }

  private void processQuotes(CSVParser parser) {

    Map<String, Integer> headersMap = parser.getHeaderMap();
    int row = 0;
    for (CSVRecord csvRow : parser) {
      row++;
      String instrumentId = CsvParserUtils.parseInstrumentId(headersMap, csvRow);
      boolean isCashInstrument = CsvParserUtils.isCashInstrument(headersMap, csvRow);
      SimpleInstrument instrument = getInstrument(instrumentId, isCashInstrument).orElse(null);
      if (instrument == null) {
        log.info("Row skipped for figi:{} row:{}", instrumentId, row);
        continue;
      }
      List<Quote> quotes = headersMap.entrySet().stream()
          .map(entry -> parseQuote(csvRow, instrument, entry))
          .filter(Optional::isPresent)
          .map(Optional::get)
          .collect(Collectors.toList());

      saveHistoryQuotes(quotes, row);
    }
    log.info("Rows parsed:{}", row);
  }

  private void saveHistoryQuotes(List<Quote> quotes, int row) {
    if (CollectionUtils.isEmpty(quotes)) {
      return;
    }

    Quote firstQuote = quotes.get(0);
    Quote lastQuote = quotes.get(quotes.size() - 1);
    List<Quote> historyQuote = quotesService.getHistoryQuotes(
        firstQuote.getInstrumentId(),
        lastQuote.getDate(),
        firstQuote.getDate()
    );

    Map<LocalDate, List<Quote>> dbDateQuoteMap = historyQuote.stream()
        .collect(Collectors.groupingBy(Quote::getDate));

    List<Quote> updatedQuotes = quotes.stream()
        .flatMap(quote -> mergeQuote(dbDateQuoteMap, quote))
        .collect(Collectors.toList());

    quotesService.insert(updatedQuotes);

    log.info(
        "saved quote with figi:{} and date:{} / {} row:{}",
        firstQuote.getInstrumentId(),
        firstQuote.getDate(),
        lastQuote.getDate(),
        row
    );
  }

  private Stream<Quote> mergeQuote(Map<LocalDate, List<Quote>> dbDateQuoteMap, Quote quote) {
    quote.setTime(LocalTime.now());
    List<Quote> dbQuotes = dbDateQuoteMap.get(quote.getDate());
    if (CollectionUtils.isEmpty(dbQuotes)) {
      return Stream.of(quote);
    }
    return dbQuotes.stream().map(dbQuote -> dbQuote.merge(quote));
  }

  private Optional<Quote> parseQuote(
      CSVRecord csvRow,
      SimpleInstrument instrument,
      Entry<String, Integer> entry
  ) {
    String headerName = entry.getKey();
    Integer columnIndex = entry.getValue();

    return CsvParserUtils.parseDate(headerName)
        .flatMap(date ->
            CsvParserUtils.parseBigDecimal(csvRow, columnIndex)
                .filter(price -> !price.equals(BigDecimal.ZERO))
                .map(price -> parseQuote(instrument, price, date))
        );
  }


  private Optional<SimpleInstrument> getInstrument(String instrumentId, boolean isCash) {
    if (isCash) {
      return Optional.of(makeCashInstrument(instrumentId));
    } else {
      return instrumentsService.getSimpleInstrument(instrumentId);
    }
  }

  private SimpleInstrument makeCashInstrument(String instrumentId) {
    return SimpleInstrument.builder()
        .isin(instrumentId)
        .exchange(Exchange.OTC)
        .type(InstrumentType.CURRENCY)
        .id(instrumentId)
        .currency(CASH_CURRENCY)
        .build();
  }

  private Quote parseQuote(SimpleInstrument instrument, BigDecimal price, LocalDate date) {
    Quote.QuoteBuilder quoteBuilder = Quote.builder()
        .isin(instrument.getIsin())
        .exchange(instrument.getExchange())
        .instrumentId(instrument.getId())
        .currency(instrument.getCurrency())
        .type(instrument.getType())
        .date(getNextWorkingDay(date));

    if (instrument.getType() == InstrumentType.BOND) {
      quoteBuilder.ytwMidEod(
          price
              .setScale(DIVIDEND_ACCURACY, RoundingMode.HALF_UP)
              .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
      );
    } else {
      quoteBuilder.close(price);
    }

    return quoteBuilder.build();
  }

  private LocalDate getNextWorkingDay(final LocalDate date) {
    final LocalDate nextDate = date.plusDays(1);
    final DayOfWeek weekDay = nextDate.getDayOfWeek();

    if (weekDay == SATURDAY) {
      return nextDate.plusDays(2);
    }
    return nextDate;
  }
}