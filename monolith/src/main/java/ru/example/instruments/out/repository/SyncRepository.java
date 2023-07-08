package ru.example.instruments.out.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.Sync;


public interface SyncRepository extends MongoRepository<Sync, String>, QueryByExampleExecutor<Sync> {

    void deleteAllByIsinAndExchange(String isin, Exchange exchange);
}
