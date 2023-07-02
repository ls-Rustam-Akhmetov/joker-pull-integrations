package ru.bcs.perseus.bloomberg.model.db;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "request_log")
@Builder(toBuilder = true)
@TypeAlias("request_log")
@Data
public class RequestLog {

    @Id
    private String id;

    private String responseId;
    private List<Sync> syncs;
    private RequestType requestType;
    private LocalDateTime requestDateTime;
    private RequestLogStatus status;
    private LocalDateTime lastTryDateTime;
    private int triesNumber;
}
