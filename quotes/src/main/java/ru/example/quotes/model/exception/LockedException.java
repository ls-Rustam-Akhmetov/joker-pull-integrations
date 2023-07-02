package ru.example.quotes.model.exception;

import org.springframework.http.HttpStatus;

public class LockedException extends HttpException {

    LockedException(String message, String detailed) {
        super(HttpStatus.LOCKED, message, detailed);
    }

    public LockedException(String message) {
        this(message, null);
    }
}
