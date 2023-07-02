package ru.bcs.perseus.bloomberg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bcs.perseus.bloomberg.model.dto.ExceptionResponse;
import ru.bcs.perseus.bloomberg.model.exception.HttpException;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ExceptionResponse> handleHttpException(HttpException e) {
        exceptionLog(e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ExceptionResponse(e.getMessage(), e.getDetailed()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnpredictedExceptions(Exception e) {
        exceptionLog(e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(e.getMessage(), null));
    }

    private void exceptionLog(Exception e) {
        log.warn(e.getMessage(), e);
    }

}
