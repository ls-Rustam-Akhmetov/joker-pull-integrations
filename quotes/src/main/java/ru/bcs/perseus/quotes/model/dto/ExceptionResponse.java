package ru.bcs.perseus.quotes.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String message;
    private final String detailed;

    public ExceptionResponse(
            @JsonProperty(value = "message", required = true) String message,
            @JsonProperty("detailed") String detailed
    ) {
        this.message = message;
        this.detailed = detailed;
    }
}
