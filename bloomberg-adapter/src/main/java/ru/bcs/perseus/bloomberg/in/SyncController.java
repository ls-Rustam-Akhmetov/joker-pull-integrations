package ru.bcs.perseus.bloomberg.in;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.bcs.perseus.bloomberg.model.Exchange;
import ru.bcs.perseus.bloomberg.model.db.Sync;
import ru.bcs.perseus.bloomberg.model.dto.MiniSyncDto;
import ru.bcs.perseus.bloomberg.model.dto.SyncDto;
import ru.bcs.perseus.bloomberg.service.SyncService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("synchronizations")
@RequiredArgsConstructor
public class SyncController {

    private final SyncService service;

    @DeleteMapping("byId")
    public void delete(@RequestBody List<MiniSyncDto> miniSyncDtos) {
        List<Sync> syncs = miniSyncDtos.stream().map(MiniSyncDto::convert).collect(Collectors.toList());
        service.delete(syncs);
    }

    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }

    @PostMapping
    public void save(@RequestBody List<MiniSyncDto> instrumentIds) {

        List<Sync> correctedSyncs = instrumentIds.stream()
                .map(MiniSyncDto::convert)
                .map(service::makeCorrectSync)
                .toList();

        Set<String> ids = correctedSyncs.stream()
                .map(Sync::getId)
                .collect(Collectors.toSet());

        List<Sync> existedSyncs = service.find(ids);
        Set<String> existedIds = existedSyncs.stream()
                .map(Sync::getId)
                .collect(Collectors.toSet());

        List<Sync> syncsToSave = correctedSyncs.stream()
                .filter(sync -> !existedIds.contains(sync.getId())) // if sync exist don't save
                .collect(Collectors.toList());

        service.save(syncsToSave);
    }

    @GetMapping
    public List<SyncDto> get(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "isin", required = false) String isin,
            @RequestParam(value = "exchange", required = false) Exchange exchange
    ) {
        List<Sync> syncs = service.get(new Sync(isin, exchange), PageRequest.of(page, size));
        return syncs.stream().map(SyncDto::new).toList();
    }
}
