package ru.example.instruments.model.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class HttpException extends RuntimeException {

    @JsonProperty("http_status")
    private final HttpStatus httpStatus;
    private final String message;
    private final String detailed;

    HttpException(
            @JsonProperty(value = "http_status", required = true) HttpStatus httpStatus,
            @JsonProperty("message") String message,
            @JsonProperty("detailed") String detailed
    ) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.detailed = detailed;
    }

}
