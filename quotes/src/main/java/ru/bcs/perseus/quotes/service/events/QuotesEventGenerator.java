package ru.bcs.perseus.quotes.service.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bcs.perseus.quotes.mapper.QuotesMapper;
import ru.bcs.perseus.quotes.model.dto.QuoteEventDto;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.stream.QuotesBinding;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuotesEventGenerator {

    private final QuotesMapper quotesMapper;
    private final QuotesBinding quotesBinding;

    public void spreadEvent(Quote quote) {
        final QuoteEventDto quoteEvent = quotesMapper.mapQuoteEvent(quote);
        quotesBinding.quoteEventsChannel().send(withPayload(quoteEvent).build());
    }
}
