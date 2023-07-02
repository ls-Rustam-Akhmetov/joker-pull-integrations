package ru.bcs.perseus.quotes.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InstrumentType {
    BOND("bond"),
    EQUITY("equity"),
    OPTION("option"),
    CURRENCY("currency"),
    FUTURES("futures");

    private String title;

    InstrumentType(String title) {
        this.title = title;
    }

    @JsonValue
    public String getTitle() {
        return title;
    }
}
