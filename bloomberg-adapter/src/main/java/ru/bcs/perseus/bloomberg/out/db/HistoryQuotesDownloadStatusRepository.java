package ru.bcs.perseus.bloomberg.out.db;

import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.bcs.perseus.bloomberg.model.db.HistoryQuotesDownloadStatus;

public interface HistoryQuotesDownloadStatusRepository extends
    MongoRepository<HistoryQuotesDownloadStatus, String> {

  void deleteAllByInstrumentIdIn(Set<String> instrumentIds);
}
