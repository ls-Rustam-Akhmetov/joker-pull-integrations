package ru.example.instruments.out.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InstrumentRepository extends MongoRepository<Instrument, String> {

    List<Instrument> findAllByFigiIn(Collection<String> instrumentId);

    Optional<Instrument> findOneByFigiAndExchange(String instrumentId, Exchange exchange);

    @Query("{'staticData.isin': {'$in': ?0}}")
    List<Instrument> findByIsins(List<String> isins);

    @Query("{'staticData.isin': ?0 }")
    List<Instrument> findByIsin(String isin);

    Long countByType(InstrumentType type);

    List<Instrument> findByType(InstrumentType type);

    List<Instrument> findAllByTypeIn(List<InstrumentType> type, Pageable pageable);

    List<Instrument> findAllByExchange(Exchange exchange);
}
