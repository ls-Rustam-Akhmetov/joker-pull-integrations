package ru.example.instruments.util;

import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.bond.Bond;
import ru.example.instruments.model.equity.Equity;

import java.util.List;
import java.util.stream.Collectors;

public class InstrumentUtils {

    private InstrumentUtils() {
    }

    public static List<Equity> getEquities(List<Instrument> foundInstruments) {
        return foundInstruments.stream()
                .filter(i -> InstrumentType.EQUITY.equals(i.getType()))
                .map(Equity.class::cast)
                .collect(Collectors.toList());
    }

    public static List<Bond> getBonds(List<Instrument> foundInstruments) {
        return foundInstruments.stream()
                .filter(i -> InstrumentType.BOND.equals(i.getType()))
                .map(Bond.class::cast)
                .collect(Collectors.toList());
    }

    public static List<Instrument> getCash(List<Instrument> foundInstruments) {
        return foundInstruments.stream()
                .filter(i -> InstrumentType.CURRENCY.equals(i.getType()))
                .collect(Collectors.toList());
    }
}
