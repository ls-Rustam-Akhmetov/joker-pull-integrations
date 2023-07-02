package ru.example.instruments.model.dto.bond;

import lombok.Data;

@Data
public class BondRecommendationDto {

    private String base;
    private String ib;
    private String amc;
    private String specialCase;
}
