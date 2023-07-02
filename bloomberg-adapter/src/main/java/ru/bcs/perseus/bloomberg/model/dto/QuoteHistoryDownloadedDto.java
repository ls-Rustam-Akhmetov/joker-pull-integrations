package ru.bcs.perseus.bloomberg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class QuoteHistoryDownloadedDto {

    private String instrumentId;

}
