package ru.example.bloomberg.model.instrument;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InstrumentType {
    BOND("bond"),
    EQUITY("equity"),
    OPTION("option"),
    CURRENCY("currency"),
    FUTURES("futures");

    private final String title;

    InstrumentType(String title) {
        this.title = title;
    }

    @JsonValue
    public String getTitle() {
        return title;
    }
}
