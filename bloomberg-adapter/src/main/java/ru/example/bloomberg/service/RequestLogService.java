package ru.example.bloomberg.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.example.bloomberg.model.db.RequestLog;
import ru.example.bloomberg.model.db.RequestLogStatus;
import ru.example.bloomberg.out.db.RequestLogRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    public List<RequestLog> getPendingRequestsByLastTryTimeBefore(LocalDateTime dateTime) {
        if (dateTime == null) {
            return Collections.emptyList();
        }
        return requestLogRepository.getPendingRequestsByLastTryTime(dateTime);
    }

    public List<RequestLog> getRequestLogsByStatusesSortedByLastTryDate(List<RequestLogStatus> status,
                                                                        int page, int size) {
        if (CollectionUtils.isEmpty(status)) {
            return Collections.emptyList();
        }
        return requestLogRepository.getRequestLogsByStatusIn(
                status,
                makePageRequestSortedByLastTryDate(page, size)
        );
    }

    public void save(RequestLog requestLog) {
        removeLogsOlderThanMonth();
        requestLogRepository.save(requestLog);
    }

    private void removeLogsOlderThanMonth() {
        requestLogRepository
                .deleteAllByLastTryDateTimeBefore(LocalDate.now().minusMonths(1).atStartOfDay());
    }

    private PageRequest makePageRequestSortedByLastTryDate(int page, int size) {
        return PageRequest.of(page, size, Sort.Direction.DESC, "lastTryDateTime");
    }
}
