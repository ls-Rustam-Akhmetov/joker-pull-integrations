package ru.example.instruments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "instruments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instrument {

    @Id
    private String id;

    private String figi;
    private Exchange exchange;
    private String blExchange;
    private String marketSector;
    private Integer marketSectorId;

    @LastModifiedDate
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
    private String currencyQuoteFactor;

    private String marketStatus;
}
