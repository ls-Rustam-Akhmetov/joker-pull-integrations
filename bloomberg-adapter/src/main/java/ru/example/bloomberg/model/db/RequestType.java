package ru.example.bloomberg.model.db;

public enum RequestType {
    INSTRUMENT,
    QUOTE,
    COMBINED,  // quotes and instrument request at the same time
    QUOTES_HISTORY,
    DIVIDENDS
}
