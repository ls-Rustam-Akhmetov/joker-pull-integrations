package ru.example.instruments.model.bond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BondRecommendation {

    private String base;
    private String ib;
    private String amc;
    private String specialCase;
}
