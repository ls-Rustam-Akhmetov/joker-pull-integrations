package ru.bcs.perseus.quotes.config;

import static feign.FeignException.errorStatus;

import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import ru.bcs.perseus.quotes.model.exception.NotAuthorizedHttpException;
import ru.bcs.perseus.quotes.model.exception.NotFoundException;

@Configuration
@EnableFeignClients(basePackages = {"ru.bcs.perseus"})
public class FeignConfig {

  @Bean
  public Logger logger() {
    return new Slf4jLogger();
  }

  @Bean
  public Logger.Level loggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return (methodKey, response) -> {
      if (HttpStatus.NOT_FOUND.value() == response.status()) {
        return new NotFoundException(response.reason());
      } else if (HttpStatus.UNAUTHORIZED.value() == response.status()) {
        return new NotAuthorizedHttpException(response.reason());
      } else {
        return errorStatus(methodKey, response);
      }
    };
  }
}
