package ru.example.instruments.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.dto.ExceptionResponse;
import ru.example.instruments.model.exception.HttpException;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;


@ControllerAdvice
@Slf4j
public class ControllersAdvice {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ExceptionResponse> handleHttpException(HttpException e) {
        exceptionLog(e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(
                        new ExceptionResponse(e.getMessage(), e.getDetailed())
                );
    }

    private void exceptionLog(Exception e) {
        log.warn(e.getMessage(), e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnpredictedExceptions(Exception e) {
        exceptionLog(e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ExceptionResponse(e.getMessage(), null)
                );
    }

    @InitBinder
    public void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(InstrumentType.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(final String text) {
                setValue(
                        Arrays.stream(InstrumentType.values())
                                .filter(type -> type.getTitle().equalsIgnoreCase(text))
                                .findFirst().orElse(null)
                );
            }
        });
    }
}
