package ru.bcs.perseus.bloomberg.model.instrument;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dividend {

  private String figi;
  private LocalDate declaredDate;
  private LocalDate exDividendDate;
  private LocalDate recordDate;
  private LocalDate payDate;
  private BigDecimal amount;
  private String frequency;
  private String dividendType;
}
