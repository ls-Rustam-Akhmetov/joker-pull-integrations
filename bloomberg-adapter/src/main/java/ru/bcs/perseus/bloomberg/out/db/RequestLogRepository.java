package ru.bcs.perseus.bloomberg.out.db;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.bcs.perseus.bloomberg.model.db.RequestLog;
import ru.bcs.perseus.bloomberg.model.db.RequestLogStatus;

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
