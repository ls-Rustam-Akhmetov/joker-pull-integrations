package ru.bcs.perseus.bloomberg.model.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("squid:S2166")
public class NotAuthorizedHttpException extends HttpException {

  public NotAuthorizedHttpException(String message, String detailed) {
    super(HttpStatus.UNAUTHORIZED, message, detailed);
  }

  public NotAuthorizedHttpException(String message) {
    super(HttpStatus.UNAUTHORIZED, message, null);
  }
}
