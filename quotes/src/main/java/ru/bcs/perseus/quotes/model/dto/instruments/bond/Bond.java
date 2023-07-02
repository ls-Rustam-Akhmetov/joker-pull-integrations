package ru.bcs.perseus.quotes.model.dto.instruments.bond;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import ru.bcs.perseus.quotes.model.dto.instruments.Instrument;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bond extends Instrument {
    private BondStaticDTO staticData;
}
