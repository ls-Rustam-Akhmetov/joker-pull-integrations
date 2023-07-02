package ru.example.quotes.out.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.example.quotes.model.quotes.Exchange;
import ru.example.quotes.model.quotes.Quote;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuotesRepository extends MongoRepository<Quote, String> {

    Quote findByInstrumentIdAndExchangeAndDate(String instrumentId, Exchange exchangeType,
                                               LocalDate date);

    List<Quote> findByInstrumentIdAndDateBetween(
            String instrumentId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Quote> findAllByInstrumentId(String instrumentId);

    List<Quote> findByIsinInAndDate(Collection<String> isins, LocalDate date);

    List<Quote> findByInstrumentIdInAndDate(Collection<String> instrumentIds, LocalDate date,
                                            Pageable pageable);

    Optional<Quote> findFirstByIsinAndDate(String isin, LocalDate date);

    Optional<Quote> findFirstByIsinAndDateBeforeOrderByDateDesc(String isin, LocalDate date);

    Optional<Quote> findFirstByInstrumentIdOrderByDateDesc(String instrumentId);

    List<Quote> findByInstrumentIdOrderByDateDesc(String instrumentId, Pageable pageable);

}
