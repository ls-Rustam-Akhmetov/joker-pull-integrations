package ru.example.bloomberg.out.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ru.example.bloomberg.model.Exchange;
import ru.example.bloomberg.model.db.Sync;

public interface SyncRepository extends MongoRepository<Sync, String>, QueryByExampleExecutor<Sync> {

    void deleteAllByIsinAndExchange(String isin, Exchange exchange);
}
