package ru.bcs.perseus.quotes.service;

import org.junit.Test;
import ru.bcs.perseus.quotes.model.quotes.Quote;
import ru.bcs.perseus.quotes.repository.QuotesMongoTemplateRepository;
import ru.bcs.perseus.quotes.repository.QuotesRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuoteServiceTest {

    private final QuotesRepository quoteRepository = mock(QuotesRepository.class);
    private final QuotesCacheService quotesCacheService = mock(QuotesCacheService.class);
    private final QuotesMongoTemplateRepository QuotesMongoTemplateRepository = mock(QuotesMongoTemplateRepository.class);
    private final QuotesService quoteService = new QuotesService(quoteRepository, quotesCacheService, QuotesMongoTemplateRepository);

    @Test
    public void getQuotesShouldReturnWithoutDuplicate() {
        Quote quote1 = Quote.builder().instrumentId("id").build();
        Quote quote2 = Quote.builder().instrumentId("id").build();
        Quote quote3 = Quote.builder().instrumentId("quoteId").build();
        Quote quote4 = Quote.builder().instrumentId("quoteId").build();
        Quote quote5 = Quote.builder().instrumentId("quoteId").build();
        Quote quote6 = Quote.builder().instrumentId("quoteId").build();
        Quote quote7 = Quote.builder().instrumentId("ids").build();
        Quote quote8 = Quote.builder().instrumentId("idf").build();
        List<Quote> quotes = Arrays.asList(quote1, quote2, quote3, quote4, quote5, quote6, quote7, quote8);

        when(quoteRepository.findByInstrumentIdInAndDate(any(), any(), any())).thenReturn(quotes);

        List<Quote> quotesUnique = quoteService.getQuotesByInstruments(Collections.emptyList());
        assertEquals(4, quotesUnique.size());
    }

}