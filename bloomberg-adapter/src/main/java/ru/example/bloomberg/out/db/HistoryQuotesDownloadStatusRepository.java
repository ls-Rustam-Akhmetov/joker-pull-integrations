package ru.example.bloomberg.out.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.example.bloomberg.model.db.HistoryQuotesDownloadStatus;

import java.util.Set;

public interface HistoryQuotesDownloadStatusRepository extends MongoRepository<HistoryQuotesDownloadStatus, String> {

    void deleteAllByInstrumentIdIn(Set<String> instrumentIds);
}
