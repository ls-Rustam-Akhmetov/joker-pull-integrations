package ru.bcs.perseus.bloomberg.out.ws.helper;

import com.bloomberg.services.dlws.BulkArray;
import com.bloomberg.services.dlws.BulkArrayEntry;
import com.bloomberg.services.dlws.Data;
import com.bloomberg.services.dlws.InstrumentData;
import lombok.extern.slf4j.Slf4j;
import ru.bcs.perseus.bloomberg.model.MarketSector;
import ru.bcs.perseus.bloomberg.model.instrument.Dividend;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentV2;
import ru.bcs.perseus.bloomberg.model.quote.Quote;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.DATE_FORMATTER;
import static ru.bcs.perseus.bloomberg.out.ws.helper.FieldHelper.getMarketSector;

@Slf4j
public class ResponseParser {

    private static final int DECLARED_DATE_ORDER = 0;
    private static final int EX_DIVIDEND_DATE_ORDER = 1;
    private static final int RECORD_DATE_ORDER = 2;
    private static final int PAY_DATE_ORDER = 3;
    private static final int AMOUNT_ORDER = 4;
    private static final int FREQUENCY_ORDER = 5;
    private static final int DIVIDEND_TYPE_ORDER = 6;

    private ResponseParser() {
    }

    /**
     * @should return correct result
     */
    public static List<Instrument> retrieveInstruments(List<InstrumentData> instrumentDataList,
                                                       List<String> fields) {
        return instrumentDataList.stream()
                .map(instrumentData -> createInstrument(instrumentData, fields))
                .collect(Collectors.toList());
    }

    public static List<Dividend> retrieveDividends(final List<InstrumentData> instrumentDataList) {
        return instrumentDataList
                .stream()
                .flatMap(ResponseParser::createDividend)
                .collect(Collectors.toList());
    }

    private static Stream<Dividend> createDividend(final InstrumentData instrumentData) {

        final List<Data> data = instrumentData.getData();
        if (data == null || data.isEmpty()) {
            return Stream.empty();
        }

        final Data firstData = data.get(0);

        if (firstData == null) {
            return Stream.empty();
        }

        final List<BulkArray> bulkArrays = firstData.getBulkarray();

        if (bulkArrays == null) {
            return Stream.empty();
        }

        return bulkArrays
                .stream()
                .map(bulkArray -> getDividend(instrumentData.getInstrument().getId(), bulkArray))
                .filter(Objects::nonNull);
    }

    private static Dividend getDividend(String figi, BulkArray bulkArray) {
        final List<BulkArrayEntry> bulkArrayEntries = bulkArray.getData();

        if (bulkArrayEntries == null) {
            return null;
        }

        final BulkArrayEntry unparsedDeclaredDate = getFieldFromBulkArray(bulkArrayEntries, DECLARED_DATE_ORDER);
        final BulkArrayEntry unparsedExDividendDate = getFieldFromBulkArray(bulkArrayEntries, EX_DIVIDEND_DATE_ORDER);
        final BulkArrayEntry unparsedRecordDate = getFieldFromBulkArray(bulkArrayEntries, RECORD_DATE_ORDER);
        final BulkArrayEntry unparsedPayDate = getFieldFromBulkArray(bulkArrayEntries, PAY_DATE_ORDER);
        final BulkArrayEntry unparsedAmount = getFieldFromBulkArray(bulkArrayEntries, AMOUNT_ORDER);
        final BulkArrayEntry unparsedFrequency = getFieldFromBulkArray(bulkArrayEntries, FREQUENCY_ORDER);
        final BulkArrayEntry unparsedDividendType = getFieldFromBulkArray(bulkArrayEntries, DIVIDEND_TYPE_ORDER);

        final LocalDate declaredDate = getDateField(unparsedDeclaredDate != null ? unparsedDeclaredDate.getValue() : null);
        final LocalDate exDividendDate = getDateField(unparsedExDividendDate != null ? unparsedExDividendDate.getValue() : null);
        final LocalDate recordDate = getDateField(unparsedRecordDate != null ? unparsedRecordDate.getValue() : null);
        final LocalDate payDate = getDateField(unparsedPayDate != null ? unparsedPayDate.getValue() : null);
        final BigDecimal amount = getBigDecimalField(unparsedAmount != null ? unparsedAmount.getValue() : null);

        final String frequency = unparsedFrequency != null ? unparsedFrequency.getValue() : null;
        final String dividendType = unparsedDividendType != null ? unparsedDividendType.getValue() : null;

        return Dividend
                .builder()
                .figi(figi)
                .dividendType(dividendType)
                .frequency(frequency)
                .recordDate(recordDate)
                .exDividendDate(exDividendDate)
                .amount(amount)
                .payDate(payDate)
                .declaredDate(declaredDate)
                .build();
    }

    private static BulkArrayEntry getFieldFromBulkArray(
            final List<BulkArrayEntry> bulkArrayEntries,
            final int declaredDateOrder
    ) {
        return bulkArrayEntries.size() >= declaredDateOrder
                ? bulkArrayEntries.get(declaredDateOrder)
                : null;
    }

    private static LocalDate getDateField(String dateString) {
        if (dateString == null) {
            log.warn("Date string is null");
            return null;
        }
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            log.warn("Unable to parse value \"{}\" to LocalDate", dateString);
            return null;
        }
    }

    private static BigDecimal getBigDecimalField(String bigDecimalString) {
        if (bigDecimalString == null) {
            log.warn("BigDecimal string is null");
            return null;
        }
        try {
            return new BigDecimal(bigDecimalString);
        } catch (NumberFormatException nfe) {
            log.warn("Unable to parse value \"{}\" to BigDecimal", bigDecimalString);
            return null;
        }
    }

    private static Instrument createInstrument(InstrumentData instrumentData, List<String> fields) {
        Map<String, String> fieldValues = makeFieldValues(instrumentData, fields);
        log.debug("Field values for instrument from bloomberg:{}", fieldValues.toString());
        return ResponseParser.createInstrument(fieldValues);
    }

    private static Map<String, String> makeFieldValues(InstrumentData instrumentData,
                                                       List<String> fields) {
        Map<String, String> fieldValues = new HashMap<>();

        for (int fieldCounter = 0; fieldCounter < fields.size(); fieldCounter++) {
            fieldValues.put(
                    fields.get(fieldCounter),
                    instrumentData.getData().get(fieldCounter).getValue()
            );
        }
        return fieldValues;
    }

    private static Instrument createInstrument(Map<String, String> fieldValues) {
        MarketSector marketSector = getMarketSector(fieldValues);
        if (marketSector == null) {
            log.warn("Not created instrument from response field {} because market sector is unknown", fieldValues);
            return null;
        }

        return switch (marketSector) {
            case EQUITY -> EquityHelper.createEquity(fieldValues);
            case CORP, GOVT -> BondHelper.createBond(fieldValues);
            default -> null;
        };
    }

    public static List<Quote> retrieveQuotes(List<InstrumentData> instrumentDataList,
                                             List<String> fields) {
        return instrumentDataList.stream()
                .map(instrumentData -> createQuote(instrumentData, fields))
                .collect(Collectors.toList());
    }

    private static Quote createQuote(InstrumentData instrumentData, List<String> fields) {
        Map<String, String> fieldValues = makeFieldValues(instrumentData, fields);
        log.debug("Field values for quotes from bloomberg:{}", fieldValues.toString());
        return QuoteHelper.createQuote(fieldValues);
    }

}
