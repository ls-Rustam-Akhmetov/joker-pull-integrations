package ru.example.quotes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.example.quotes.model.quotes.Quote;
import ru.example.quotes.repository.QuotesRepository;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "quotes")
public class QuotesCacheService {

    private final QuotesRepository repository;

    @Cacheable(key = "#instrumentId", unless = "#result == null")
    public Quote getLastQuote(String instrumentId) {
        return getLastQuoteByInstrumentId(instrumentId);
    }

    @CachePut(key = "#instrumentId", unless = "#result == null")
    public Quote refreshLastQuote(String instrumentId) {
        return getLastQuoteByInstrumentId(instrumentId);
    }

    private Quote getLastQuoteByInstrumentId(String instrumentId) {
        return repository.findFirstByInstrumentIdOrderByDateDesc(instrumentId)
                .orElse(null);
    }
}
