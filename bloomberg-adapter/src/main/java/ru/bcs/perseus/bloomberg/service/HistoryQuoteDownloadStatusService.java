package ru.bcs.perseus.bloomberg.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.bcs.perseus.bloomberg.model.db.HistoryQuotesDownloadStatus;
import ru.bcs.perseus.bloomberg.out.db.HistoryQuotesDownloadStatusRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryQuoteDownloadStatusService {

    private final HistoryQuotesDownloadStatusRepository recordRepository;

    public void insert(String instrumentId) {
        HistoryQuotesDownloadStatus record = new HistoryQuotesDownloadStatus(instrumentId);
        record.setCreatedDateTime(LocalDateTime.now());
        recordRepository.insert(record);
    }

    public void saveFinishedRecord(String instrumentId) {
        boolean isExists = findById(instrumentId).isPresent();
        if (isExists) {
            return;
        }
        HistoryQuotesDownloadStatus record = new HistoryQuotesDownloadStatus(instrumentId);
        LocalDateTime dateTime = LocalDateTime.now();
        record.setCreatedDateTime(dateTime);
        record.setFinishDownloadTime(dateTime);
        recordRepository.insert(record);
    }

    public void saveDownloadTime(String instrumentId, LocalDateTime localDateTime) {
        if (StringUtils.isBlank(instrumentId)) {
            log.warn("Warn! Blank instrumentId");
            return;
        }

        findById(instrumentId).ifPresent(record -> {
            record.setFinishDownloadTime(localDateTime);
            recordRepository.save(record);
        });
    }

    public void deleteAllByIds(Set<String> instrumentIdList) {
        recordRepository.deleteAllByInstrumentIdIn(instrumentIdList);
    }

    public List<HistoryQuotesDownloadStatus> findAll(Pageable pageable) {
        return recordRepository.findAll(pageable).getContent();
    }

    public Optional<HistoryQuotesDownloadStatus> findById(String instrumentId) {
        return findById(Collections.singleton(instrumentId)).stream().findFirst();
    }

    public List<HistoryQuotesDownloadStatus> findById(Set<String> instrumentIdList) {
        return recordRepository.findAllById(instrumentIdList);
    }
}
