package ru.bcs.perseus.quotes.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheWarmUpService {

    private final AtomicBoolean started = new AtomicBoolean(false);
    private final QuotesService quotesService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        boolean isStartedAlready = started.get();
        if (isStartedAlready) {
            // this workaround needed because spring ready and started events could be thrown multiple times, it's famous spring bug :-)
            return;
        }
        started.set(true);

        log.info("Start after startup processing");
        quotesService.refreshAllQuotesCache();
        log.info("Finish after startup processing");
    }
}
