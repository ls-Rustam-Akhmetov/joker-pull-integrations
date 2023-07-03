package ru.example.bloomberg.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.example.bloomberg.model.db.Sync;
import ru.example.bloomberg.service.BloombergSyncService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("manual/sync")
@RequiredArgsConstructor
public class ManualSyncController {

    private final BloombergSyncService service;

    @PostMapping("instruments")
    public void instrumentsSync() {
        service.syncInstruments();
    }

    @PostMapping("quotes")
    public void quotesSync() {
        service.syncQuotes();
    }

    @PostMapping("dividends")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void dividendsSync(@RequestBody final List<String> instrumentIds) {
        service.syncDividends(instrumentIds.stream().map(this::getDividendSync).collect(toList()));
    }

    private Sync getDividendSync(String instrumentId) {
        final Sync sync = new Sync();
        sync.setFigi(instrumentId);
        return sync;
    }
}
