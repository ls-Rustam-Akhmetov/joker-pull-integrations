package ru.example.instruments.model.dto.equity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EquityRecommendationDto {

    private String base; //основная рекомендация
    private String speculative; //спекулятивная рекомендация
    private BigDecimal targetPrice;//целевая цена
    private LocalDate date; //дата рекомендации
    private Integer period; //период рекомендации (месяцы)
}
