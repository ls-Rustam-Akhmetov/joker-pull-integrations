package ru.bcs.perseus.quotes.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

@SuppressWarnings("squid:S1214")
public interface QuotesBinding {

    String QUOTES = "quotes";
    String QUOTE_EVENTS = "quoteEvents";

    @Input(QUOTES)
    MessageChannel quotesChannel();

    @Output(QUOTE_EVENTS)
    MessageChannel quoteEventsChannel();
}
