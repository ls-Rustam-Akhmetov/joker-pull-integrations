package ru.bcs.perseus.bloomberg.config;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.concurrent.ExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsyncConfig {

  @Bean
  public ExecutorService executorService() {
    return
        newFixedThreadPool(getRuntime().availableProcessors() + 1);
  }
}
