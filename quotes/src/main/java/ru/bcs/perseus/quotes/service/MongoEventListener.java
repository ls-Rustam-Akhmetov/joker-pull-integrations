package ru.bcs.perseus.quotes.service;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Service;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.service.events.QuotesEventGenerator;

@Slf4j
@Service
@AllArgsConstructor
public class MongoEventListener extends AbstractMongoEventListener<Quote> {

  private final QuotesEventGenerator eventGenerator;

  @Override
  public void onAfterSave(AfterSaveEvent<Quote> event) {
    super.onAfterSave(event);

    final Quote quote = event.getSource();
    boolean isTodayQuote = quote.getDate() != null && LocalDate.now().isEqual(quote.getDate());
    if (isTodayQuote) {
      eventGenerator.spreadEvent(quote);
    }
  }
}
