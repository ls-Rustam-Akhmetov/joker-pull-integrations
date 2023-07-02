package ru.bcs.perseus.bloomberg.model.instrument;

import lombok.Data;

import java.util.Map;

@Data
public class InstrumentV2 {

    private String id; // figi
    private Map<String, Object> fields;
}
