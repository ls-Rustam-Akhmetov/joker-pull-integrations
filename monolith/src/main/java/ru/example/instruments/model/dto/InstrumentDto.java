package ru.example.instruments.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.example.instruments.model.Exchange;
import ru.example.instruments.model.InstrumentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentDto {

    @NotNull
    private String id;
    @NotNull
    private Exchange exchange;
    private String blExchange;

    private String marketSector;
    private Integer marketSectorId;
    private LocalDateTime lastModified;
    private LocalDate endOfUseDate;
    @NotNull
    private String user;
    @NotNull
    private InstrumentType type;
    private String ticker;
    @NotNull
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

    private String marketStatus;
}
