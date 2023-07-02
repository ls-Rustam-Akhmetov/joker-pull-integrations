package ru.bcs.perseus.quotes.service.events;

import static ru.bcs.perseus.quotes.stream.QuotesBinding.QUOTES;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.service.QuotesService;

@Slf4j
@Component
@RequiredArgsConstructor
public class QuotesListener {

  private final QuotesService quotesService;

  @StreamListener(QUOTES)
  public void onQuoteUpdate(final Quote quote) {
    log.debug("Receive quote: {}", quote);

    quotesService.save(quote);
  }
}
