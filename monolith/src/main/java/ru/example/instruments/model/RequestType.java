package ru.example.instruments.model;

public enum RequestType {
    INSTRUMENT,
    QUOTE,
    COMBINED,  // quotes and instrument request at the same time
    DIVIDENDS
}
