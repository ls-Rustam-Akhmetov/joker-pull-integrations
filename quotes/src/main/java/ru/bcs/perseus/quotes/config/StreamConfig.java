package ru.bcs.perseus.quotes.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import ru.bcs.perseus.quotes.stream.QuotesBinding;

@EnableBinding(QuotesBinding.class)
public class StreamConfig {
}
