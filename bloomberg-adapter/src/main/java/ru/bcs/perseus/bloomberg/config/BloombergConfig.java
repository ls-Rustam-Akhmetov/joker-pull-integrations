package ru.bcs.perseus.bloomberg.config;

import jakarta.validation.constraints.NotNull;
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
  private String keystorePath;
  private String keystorePassword;

  @NotNull
  private Integer maxInstrumentRequestLimit;
  @NotNull
  private Integer maxQuoteRequestLimit;
  @NotNull
  private Integer maxRetryLimit;
  @NotNull
  private Integer historyPeriodInDays;
}
