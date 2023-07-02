package ru.bcs.perseus.bloomberg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newFixedThreadPool;

@Configuration
public class AsyncConfig {

    @Bean
    public ExecutorService executorService() {
        return newFixedThreadPool(getRuntime().availableProcessors() + 1);
    }
}
