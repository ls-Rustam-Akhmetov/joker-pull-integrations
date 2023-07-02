package ru.bcs.perseus.quotes.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.bcs.perseus.quotes.model.quotes.Quote;

import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class QuotesMetrics implements MeterBinder {

    @Value("${quik.source.name}")
    private String quikSource;
    @Value("${bloomberg.source.name}")
    private String bloombergAdapterSource;

    private static final String QUOTE_QUIK_UPDATE_AGE_IN_DAYS = "quote.quik.update.age.days";
    private static final String QUOTE_BO_UPDATE_AGE_IN_DAYS = "quote.bo.update.age.days";


    private final QuotesService quotesService;

    @Override
    public void bindTo(MeterRegistry registry) {
        Gauge.builder(QUOTE_QUIK_UPDATE_AGE_IN_DAYS, () -> getQuotesLastUpdateAgeInDays(quikSource))
                .description("how long quik quotes not been updated")
                .register(registry);

        Gauge.builder(QUOTE_BO_UPDATE_AGE_IN_DAYS, () -> getQuotesLastUpdateAgeInDays(bloombergAdapterSource))
                .description("how long bloomberg quotes not been updated")
                .register(registry);
    }

    private int getQuotesLastUpdateAgeInDays(String source) {
        LocalDate now = LocalDate.now();
        LocalDate lastUpdateDate = quotesService.findLastUpdatedDateQuote(source)
                .map(Quote::getDate)
                .orElse(null);
        if (lastUpdateDate == null) return -1;
        Period period = Period.between(lastUpdateDate, now);
        return period.getDays();
    }
}
