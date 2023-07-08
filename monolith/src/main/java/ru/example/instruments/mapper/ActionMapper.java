package ru.example.instruments.mapper;


import ru.example.instruments.model.Dividend;
import ru.example.instruments.model.dto.ActionDto;

import java.util.List;

public class ActionMapper {

    public static final String DIVIDEND_TYPE = "dividend";

    private ActionMapper() {
    }

    public static ActionDto map(final Dividend dividend) {
        return ActionDto
                .builder()
                .type(DIVIDEND_TYPE)
                .subType(dividend.getDividendType())
                .amount(dividend.getAmount())
                .declaredDate(dividend.getDeclaredDate())
                .exDividendDate(dividend.getExDividendDate())
                .frequency(dividend.getFrequency())
                .instrumentId(dividend.getFigi())
                .paymentDate(dividend.getPayDate())
                .recordDate(dividend.getRecordDate())
                .build();
    }

    public static List<ActionDto> map(final List<Dividend> dividends) {
        return dividends.stream().map(ActionMapper::map).toList();
    }
}
