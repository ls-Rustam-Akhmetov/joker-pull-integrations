package ru.example.instruments.out.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.example.instruments.model.RequestLog;
import ru.example.instruments.model.RequestLogStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestLogRepository extends MongoRepository<RequestLog, String> {

    @Query(
            "{" +
                    "'status' : 'PENDING', " +
                    "$or : " +
                    "[" +
                    "{'lastTryDateTime' : {$lte : ?0}}, " +
                    "{ $and : [ {'lastTryDateTime' : null}, {'requestDateTime' : {$lte : ?0}} ] }" +
                    "]" +
                    "}"
    )
    List<RequestLog> getPendingRequestsByLastTryTime(LocalDateTime dateTime);

    List<RequestLog> getRequestLogsByStatusIn(List<RequestLogStatus> statuses, Pageable pageable);

    void deleteAllByLastTryDateTimeBefore(LocalDateTime localDateTime);
}
