package ru.bcs.perseus.quotes.model.dto.instruments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bcs.perseus.quotes.model.dto.instruments.bond.Bond;
import ru.bcs.perseus.quotes.model.dto.instruments.equity.Equity;


import java.util.List;

import static java.util.Collections.emptyList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoundInstrumentsDTO {

    private List<Bond> bonds = emptyList();
    private List<Equity> equities = emptyList();

}
