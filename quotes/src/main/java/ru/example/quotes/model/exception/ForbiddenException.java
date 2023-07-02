package ru.example.quotes.model.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException {

    public ForbiddenException(String message, String detailed) {
        super(HttpStatus.FORBIDDEN, message, detailed);
    }

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message, null);
    }
}
