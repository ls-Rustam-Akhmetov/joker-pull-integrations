package ru.example.instruments.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.Sync;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiniSyncDto {

    private String isin;
    private Exchange exchange;

    public Sync convert() {
        return new Sync(this.isin, this.exchange);
    }
}
