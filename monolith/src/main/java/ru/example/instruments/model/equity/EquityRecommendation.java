package ru.example.instruments.model.equity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import ru.example.instruments.util.NotNullBeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquityRecommendation {

    private String base;
    private String speculative;
    private BigDecimal targetPrice;
    private LocalDate date;
    private Integer period;

    public EquityRecommendation(EquityRecommendation update) {
        if (update != null) {
            BeanUtils.copyProperties(update, this);
        }
    }

    public EquityRecommendation merge(EquityRecommendation update) {
        EquityRecommendation ret = new EquityRecommendation(this);
        NotNullBeanUtils.copyNotNullProperties(update, ret);
        return ret;
    }
}
