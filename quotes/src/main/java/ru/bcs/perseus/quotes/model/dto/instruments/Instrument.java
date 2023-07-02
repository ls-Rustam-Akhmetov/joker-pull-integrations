package ru.bcs.perseus.quotes.model.dto.instruments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import ru.bcs.perseus.quotes.model.InstrumentType;
import ru.bcs.perseus.quotes.model.quotes.Exchange;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Instrument {

    private String id; // figi
    private Exchange exchange;
    private String blExchange;
    private String compositeFigi;

    private LocalDateTime lastModified;
    private LocalDate endOfUseDate;
    private String user;
    private InstrumentType type;
    private String ticker;
    private String source;

    private String cfiCode;
    private String bloombergCfiCode;
    private String countryIso;
    private String countryDomicile;
    private BigDecimal tradeLotSize;
    private BigDecimal quoteLotSize;
    private BigDecimal roundLotSize;
}

