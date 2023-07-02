package ru.example.bloomberg.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.example.bloomberg.model.Exchange;
import ru.example.bloomberg.model.db.Sync;
import ru.example.bloomberg.out.db.SyncRepository;
import ru.example.bloomberg.util.CurrencyHelper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncService {

    private final SyncRepository syncRepository;

    public void delete(List<Sync> syncs) {
        syncs.forEach(sync -> syncRepository.deleteAllByIsinAndExchange(sync.getIsin(), sync.getExchange()));
    }

    public void deleteAll() {
        syncRepository.deleteAll();
    }

    public void save(List<Sync> syncs) {
        syncRepository.saveAll(syncs);
    }

    public List<Sync> find(Iterable<String> ids) {
        return syncRepository.findAllById(ids);
    }

    public List<Sync> get(Sync sync, PageRequest pageRequest) {
        sync.setId(null);
        return syncRepository.findAll(Example.of(sync), pageRequest)
                .getContent();
    }

    public List<Sync> getAll() {
        return syncRepository.findAll();
    }

    public Sync makeCorrectSync(Sync sync) {
        boolean isCurrency = CurrencyHelper.isCurrency(sync.getIsin());
        if (isCurrency) {
            Sync correctedSync = new Sync(sync.getIsin(), Exchange.OTC);
            correctedSync.setFigi(sync.getFigi());
            return correctedSync;
        }
        return sync;
    }
}
