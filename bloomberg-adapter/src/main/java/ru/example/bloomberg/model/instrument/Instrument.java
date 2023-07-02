package ru.example.bloomberg.model.instrument;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.example.bloomberg.model.Exchange;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Instrument domain.
 * <p>
 * There is the staticData field which exists in each type of instrument (Equity and Bond). It's a
 * historic artifact. During going towards clean architecture we propose to drop this field and make
 * splitting to several sections (classes) by another feature.
 * <p>
 * To achieve written above we propose to make it step by step (at least do not make it worse).
 * Let's add new fields generic for each instrument (Equity, Bond, Future, Option) to Instrument
 * domain class only.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Instrument {

    private String id; // figi
    @Setter
    private Exchange exchange;
    private String blExchange;
    private String compositeFigi;
    private String marketSector;
    private Integer marketSectorId;

    private LocalDateTime lastModified;
    private LocalDate endOfUseDate;
    private String user;
    private InstrumentType type;
    private String ticker;
    private String source;
    private String currency;

    private String cfiCode;
    private String bloombergCfiCode;
    private String countryIso;
    private String countryDomicile;
    private BigDecimal tradeLotSize;
    private BigDecimal quoteLotSize;
    private BigDecimal roundLotSize;
    private String currencyQuoteFactor;

    public Instrument(Instrument instrument) {
        this.id = instrument.id;
        this.exchange = instrument.exchange;
        this.blExchange = instrument.blExchange;
        this.compositeFigi = instrument.compositeFigi;
        this.lastModified = instrument.lastModified;
        this.endOfUseDate = instrument.endOfUseDate;
        this.user = instrument.user;
        this.type = instrument.type;
        this.ticker = instrument.ticker;
        this.source = instrument.source;
        this.marketSector = instrument.marketSector;
        this.marketSectorId = instrument.marketSectorId;
        this.currency = instrument.currency;
        this.cfiCode = instrument.cfiCode;
        this.bloombergCfiCode = instrument.bloombergCfiCode;
        this.countryIso = instrument.countryIso;
        this.countryDomicile = instrument.countryDomicile;
        this.tradeLotSize = instrument.tradeLotSize;
        this.quoteLotSize = instrument.quoteLotSize;
        this.roundLotSize = instrument.roundLotSize;
        this.currencyQuoteFactor = instrument.currencyQuoteFactor;
    }

    public String getIsin() {
        return null;
    }
}
