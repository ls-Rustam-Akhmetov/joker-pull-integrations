package ru.example.instruments.model.dto;

import lombok.Data;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.Sync;


@Data
public class SyncDto {

    private String id;
    private String isin;
    private Exchange exchange;
    private String figi;

    public SyncDto(Sync sync) {
        this.id = sync.getId();
        this.isin = sync.getIsin();
        this.exchange = sync.getExchange();
        this.figi = sync.getFigi();
    }
}
