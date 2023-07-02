package ru.bcs.perseus.bloomberg.out.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ru.bcs.perseus.bloomberg.model.Exchange;
import ru.bcs.perseus.bloomberg.model.db.Sync;

public interface SyncRepository extends MongoRepository<Sync, String>, QueryByExampleExecutor<Sync> {

    void deleteAllByIsinAndExchange(String isin, Exchange exchange);
}
