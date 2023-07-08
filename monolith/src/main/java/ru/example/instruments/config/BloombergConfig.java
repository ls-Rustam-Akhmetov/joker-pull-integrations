package ru.example.instruments.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("bloomberg")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloombergConfig {

    private String address;
    private Integer maxRetryLimit;
    private Integer historyPeriodInDays;
}
