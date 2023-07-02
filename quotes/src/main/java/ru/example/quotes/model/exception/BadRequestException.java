package ru.example.quotes.model.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException {

    public BadRequestException(String message, String detailed) {
        super(HttpStatus.BAD_REQUEST, message, detailed);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message, null);
    }
}
