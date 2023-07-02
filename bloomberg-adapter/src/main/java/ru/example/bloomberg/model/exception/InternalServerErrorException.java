package ru.example.bloomberg.model.exception;

import org.springframework.http.HttpStatus;


public class InternalServerErrorException extends HttpException {

    public InternalServerErrorException(String message, String detailed) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, detailed);
    }

    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }
}
