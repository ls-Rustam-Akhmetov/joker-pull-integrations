package ru.bcs.perseus.bloomberg.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bcs.perseus.bloomberg.model.Exchange;
import ru.bcs.perseus.bloomberg.model.db.Sync;
import ru.bcs.perseus.bloomberg.model.instrument.InstrumentType;
import ru.bcs.perseus.bloomberg.service.BloombergSyncService;
import ru.bcs.perseus.bloomberg.service.SyncService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("bloombergSync")
@RequiredArgsConstructor
public class BloombergSyncController {

    private final BloombergSyncService service;
    private final SyncService syncService;

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

    @PostMapping("currency/quotes/history")
    public void currencyQuotesHistorySync(
            @RequestParam("currency") String currency,
            @RequestParam(value = "exchange", defaultValue = "OTC") Exchange exchange
    ) {
        service.syncCurrencyQuotesHistory(currency, exchange);
    }

    @PostMapping("instrument/quotes/history")
    public void instrumentQuotesHistorySync(@Valid @RequestBody HistorySyncDto dto) {
        Sync sync = new Sync(dto.getIsin(), dto.getExchange());
        sync.setFigi(dto.getFigi());
        sync.setInstrumentType(dto.getInstrumentType());
        sync.setCurrency(dto.getCurrency());
        service.syncInstrumentQuotesHistory(sync);
    }

    private Sync getDividendSync(String instrumentId) {
        final Sync sync = new Sync();
        sync.setFigi(instrumentId);
        return sync;
    }

    @Getter
    @Setter
    private static class HistorySyncDto {
        @NotBlank
        private String figi;
        @NotBlank
        private String isin;
        @NotBlank
        private String currency;
        @NotNull
        private InstrumentType instrumentType;
        @NotNull
        private Exchange exchange;
    }
}
