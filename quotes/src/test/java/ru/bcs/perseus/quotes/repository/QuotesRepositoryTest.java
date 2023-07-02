package ru.bcs.perseus.quotes.repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bcs.perseus.quotes.config.MongoTestConfiguration;
import ru.bcs.perseus.quotes.model.quotes.Quote;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MongoTestConfiguration.class, QuotesRepository.class})
public class QuotesRepositoryTest {

  @Autowired
  private QuotesRepository quotesRepository;

  @Test
  public void should_find3Quotes_whenExists5ButNotAllInclusiveInDates() {
    String instrumentId = "id";
    LocalDate today = LocalDate.now();
    LocalDate yesterday = today.minusDays(1);
    LocalDate tomorrow = today.plusDays(1);
    Quote yearlyQuote = Quote.builder().instrumentId(instrumentId).date(today.minusDays(2)).build();
    Quote yesterdayQuote = Quote.builder().instrumentId(instrumentId).date(yesterday).build();
    Quote todayQuote = Quote.builder().instrumentId(instrumentId).date(today).build();
    Quote tomorrowQuote = Quote.builder().instrumentId(instrumentId).date(tomorrow).build();
    Quote oldQuote = Quote.builder().instrumentId(instrumentId).date(today.plusDays(2)).build();

    quotesRepository.saveAll(
        Arrays.asList(yearlyQuote, yesterdayQuote, todayQuote, tomorrowQuote, oldQuote)
    );

    List<Quote> quotes = quotesRepository
        .findByInstrumentIdAndDateBetween(instrumentId, yesterday, tomorrow);

    Assertions.assertThat(quotes.size()).isEqualTo(1);
    Assertions.assertThat(quotes.get(0).getDate()).isEqualTo(today);
  }

}
