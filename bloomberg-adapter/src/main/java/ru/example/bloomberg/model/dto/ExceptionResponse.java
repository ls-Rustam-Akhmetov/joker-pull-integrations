package ru.example.bloomberg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private final String message;
    private final String detailed;
}
