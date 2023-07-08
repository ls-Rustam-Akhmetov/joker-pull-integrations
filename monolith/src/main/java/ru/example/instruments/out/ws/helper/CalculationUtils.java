package ru.example.instruments.out.ws.helper;

import java.math.BigDecimal;

public class CalculationUtils {

    private CalculationUtils() {
    }

    public static BigDecimal getAccruedInterest(BigDecimal nominal, BigDecimal intAcc) {
        return intAcc != null && nominal != null ? intAcc.multiply(nominal) : null;
    }
}
