package ru.bcs.perseus.quotes.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

public class CsvParserUtils {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  private static final String FIGI_FIELD = "figi";
  private static final String NAME_FIELD = "Name";
  private static final String CURRENCY_FIELD = "Curncy";

  private CsvParserUtils() {
  }

  public static Optional<BigDecimal> parseBigDecimal(CSVRecord csvRow, Integer columnIndex) {
    String cellValue = csvRow.get(columnIndex);
    if (StringUtils.isBlank(cellValue)) {
      return Optional.empty();
    }
    try {
      BigDecimal value = new BigDecimal(cellValue);
      if (value.signum() == 0) {
        return Optional.empty();
      } else {
        return Optional.of(value);
      }
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  public static String parseInstrumentId(Map<String, Integer> headersMap, CSVRecord row) {
    Integer headerIndex = headersMap.get(FIGI_FIELD);
    return row.get(headerIndex);
  }

  public static Boolean isCashInstrument(Map<String, Integer> headersMap, CSVRecord csvRow) {
    return Optional.ofNullable(headersMap.get(NAME_FIELD))
        .map(csvRow::get)
        .map(name -> name.contains(CURRENCY_FIELD))
        .orElse(false);
  }

  public static Optional<LocalDate> parseDate(String value) {
    try {
      return Optional.ofNullable(LocalDate.parse(value, DATE_FORMATTER));
    } catch (DateTimeParseException e) {
      // if header is not date then don't parse
      return Optional.empty();
    }
  }
}
