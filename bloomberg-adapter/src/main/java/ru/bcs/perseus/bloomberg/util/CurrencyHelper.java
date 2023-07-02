package ru.bcs.perseus.bloomberg.util;

import ru.bcs.perseus.bloomberg.model.instrument.InstrumentType;

import java.util.regex.Pattern;

import static ru.bcs.perseus.bloomberg.model.instrument.InstrumentType.CURRENCY;

public class CurrencyHelper {

    private static final Pattern CURRENCY_PATTER = Pattern.compile("\\b[a-zA-Z]{6}\\b");

    private CurrencyHelper() {
    }

    public static boolean isCurrency(String isin) {
        return CURRENCY_PATTER.matcher(isin).matches();
    }

    public static boolean isCurrency(InstrumentType instrumentType) {
        return CURRENCY == instrumentType;
    }
}
