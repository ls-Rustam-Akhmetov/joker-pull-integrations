package ru.bcs.perseus.bloomberg.model.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("squid:S2166")
public class NotFoundException extends HttpException {

    public NotFoundException(String message, String detailed) {
        super(HttpStatus.NOT_FOUND, message, detailed);
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message, null);
    }
}
