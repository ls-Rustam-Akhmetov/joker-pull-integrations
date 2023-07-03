package ru.example.bloomberg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.example.bloomberg.model.db.RequestType;
import ru.example.bloomberg.model.db.Sync;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BloombergSyncService {

    private final BloombergService bloombergService;
    private final SyncService syncService;

    @Scheduled(cron = "${bloomberg.instrument-sync-time}")
    public void syncInstruments() {
        syncByRequestType(RequestType.INSTRUMENT);
    }

    @Scheduled(cron = "${bloomberg.quote-sync-time}")
    public void syncQuotes() {
        syncByRequestType(RequestType.QUOTE);
    }

    public void syncDividends(final List<Sync> syncs) {
        bloombergService.requestForDataPreparation(syncs, RequestType.DIVIDENDS);
    }

    private void syncByRequestType(RequestType requestType) {
        List<Sync> syncs = syncService.getAll();
        bloombergService.requestForDataPreparation(syncs, requestType);
    }
}
