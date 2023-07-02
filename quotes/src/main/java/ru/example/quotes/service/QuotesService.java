package ru.example.quotes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.example.quotes.model.exception.BadRequestException;
import ru.example.quotes.model.exception.NotFoundException;
import ru.example.quotes.model.quotes.Quote;
import ru.example.quotes.out.db.QuotesMongoTemplateRepository;
import ru.example.quotes.out.db.QuotesRepository;
import ru.example.quotes.out.kafka.QuoteKafkaProducer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuotesService {

    private static final int PAGE = 0;
    private static final int SIZE = 10000;
    private static final Pageable PAGEABLE = PageRequest.of(PAGE, SIZE);

    private final QuotesRepository repository;
    private final QuotesCacheService quotesCacheService;
    private final QuotesMongoTemplateRepository quotesMongoTemplateRepository;
    private final QuoteKafkaProducer quoteKafkaProducer;

    @Async
    public void refreshAllQuotesCache() {
        LocalTime start = LocalTime.now();
        log.info("Started refresh cache");

        quotesMongoTemplateRepository.getDistinctInstrumentIds()
                .forEach(quotesCacheService::refreshLastQuote);

        LocalTime end = LocalTime.now();
        Duration duration = Duration.between(start, end);
        log.info("Finished refresh cache for '{}' seconds", duration.getSeconds());
    }

    public List<Quote> getLastQuotes(List<String> instrumentIds) {
        return instrumentIds.stream()
                .map(quotesCacheService::getLastQuote)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    /**
     * inclusive byDates search of quotes
     */
    public List<Quote> getHistoryQuotes(String instrumentId, LocalDate startDate, LocalDate endDate) {
        return repository.findByInstrumentIdAndDateBetween(
                instrumentId,
                startDate.minusDays(1),
                endDate.plusDays(1)
        );
    }

    public List<Quote> getHistoryQuotes(final String instrumentId) {
        return repository.findAllByInstrumentId(instrumentId);
    }

    public List<Quote> getQuotesByIsins(Collection<String> isins, LocalDate date) {
        return repository.findByIsinInAndDate(isins, date);
    }

    public List<Quote> getQuotesByInstruments(Collection<String> instrumentIds) {
        List<Quote> quotes = repository
                .findByInstrumentIdInAndDate(instrumentIds, LocalDate.now(), PAGEABLE);
        Map<String, Quote> quoteMap = quotes.stream()
                .collect(Collectors.toMap(
                        Quote::getInstrumentId,
                        Function.identity(),
                        (q1, q2) -> q1)
                );
        return new ArrayList<>(quoteMap.values());
    }

    public List<Quote> find(Quote quote, PageRequest pageRequest) {
        return repository.findAll(Example.of(quote), pageRequest).getContent();
    }

    public Quote getQuote(String isin, LocalDate date) {
        LocalDate searchDate = firstNonNull(date, LocalDate.now());
        return repository.findFirstByIsinAndDate(isin, searchDate)
                .orElseThrow(() -> getNotFoundException(isin, date));
    }

    private NotFoundException getNotFoundException(String instrumentId, LocalDate date) {
        return new NotFoundException(format("No quote was found " +
                "for id [%s] and date [%s]", instrumentId, date));
    }

    public List<Quote> getLastQuotes(String instrumentId, Pageable pageable) {
        return repository
                .findByInstrumentIdOrderByDateDesc(instrumentId, pageable);
    }

    public Quote getLastQuote(String isin, LocalDate date) {
        LocalDate currentDate = firstNonNull(date, LocalDate.now());
        LocalDate searchDateBefore = currentDate.plusDays(1);
        return repository.findFirstByIsinAndDateBeforeOrderByDateDesc(isin, searchDateBefore)
                .orElseThrow(() -> getNotFoundException(isin, currentDate));
    }

    public void save(Quote quote) {
        checkQuoteNotNull(quote);
        setQuoteDateTime(quote);

        Quote savedQuote = repository.save(quote);
        quotesCacheService.refreshLastQuote(savedQuote.getInstrumentId());
        quoteKafkaProducer.sendQuote(savedQuote);
    }

    private void checkQuoteNotNull(Quote quote) {
        if (quote == null) {
            throw new BadRequestException("Can't save null quote");
        }
    }

    private void setQuoteDateTime(Quote quote) {
        quote.setTime(firstNonNull(quote.getTime(), LocalTime.now()));
        quote.setDate(firstNonNull(quote.getDate(), LocalDate.now()));
    }
}
