package ru.example.bloomberg.out.ws.helper;

import java.math.BigDecimal;

public class BloombergUtils {

    private BloombergUtils() {
    }

    public static BigDecimal getAccruedInterest(BigDecimal nominal, BigDecimal intAcc) {
        return intAcc != null && nominal != null ? intAcc.multiply(nominal) : null;
    }
}
