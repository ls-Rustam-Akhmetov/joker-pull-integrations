package ru.example.instruments.model.equity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.Rating;

@EqualsAndHashCode(callSuper = true)
@TypeAlias("equity")
@Data
public class Equity extends Instrument {

    private EquityStatic staticData;
    private EquityRecommendation recommendation;
    private Rating rating;

    public Equity() {
        this.setType(InstrumentType.EQUITY);
    }
}
