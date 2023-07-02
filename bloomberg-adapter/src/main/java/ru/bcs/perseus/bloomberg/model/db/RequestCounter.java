package ru.bcs.perseus.bloomberg.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "request_counter")
@TypeAlias("request_counter")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCounter {

    @Id
    private String id;
    @Indexed
    private LocalDate date;
    private RequestType requestType;
    private int requestCount;

    public RequestCounter(int requestCount, RequestType requestType) {
        this.date = LocalDate.now();
        this.requestCount = requestCount;
        this.requestType = requestType;
    }
}
