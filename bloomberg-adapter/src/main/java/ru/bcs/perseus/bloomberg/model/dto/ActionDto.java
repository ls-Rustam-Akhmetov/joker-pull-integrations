package ru.bcs.perseus.bloomberg.model.dto;

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
public class ActionDto {

  private String instrumentId;
  private String type;
  private String subType;
  private String frequency;
  private LocalDate declaredDate;
  private LocalDate exDividendDate;
  private LocalDate recordDate;
  private LocalDate paymentDate;
  private BigDecimal amount;
}