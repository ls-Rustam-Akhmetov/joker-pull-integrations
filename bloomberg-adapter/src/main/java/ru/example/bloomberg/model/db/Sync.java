package ru.example.bloomberg.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import ru.example.bloomberg.model.Exchange;
import ru.example.bloomberg.model.instrument.InstrumentType;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Sync {

    @Id
    private String id;
    private String isin;
    private Exchange exchange;
    private String figi;
    private InstrumentType instrumentType;
    private String currency;

    public Sync(String isin, Exchange exchange) {
        this.isin = isin;
        this.exchange = exchange;
        this.id = makeId(isin, exchange);
    }

    private String makeId(String isin, Exchange exchange) {
        if (StringUtils.isNotBlank(isin) && exchange != null) {
            return isin + "_" + exchange;
        }
        if (exchange != null) {
            return exchange.toString();
        }
        if (StringUtils.isNotBlank(isin)) {
            return isin;
        }
        return null;
    }
}
