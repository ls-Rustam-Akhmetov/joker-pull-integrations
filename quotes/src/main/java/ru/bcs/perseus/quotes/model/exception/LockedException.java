package ru.bcs.perseus.quotes.model.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("squid:S2166")
public class LockedException extends HttpException {

    LockedException(String message, String detailed) {
        super(HttpStatus.LOCKED, message, detailed);
    }

    public LockedException(String message) {
        this(message, null);
    }
}
