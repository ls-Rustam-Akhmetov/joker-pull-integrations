package ru.example.instruments.model.bond;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import ru.example.instruments.model.Instrument;
import ru.example.instruments.model.InstrumentType;
import ru.example.instruments.model.Rating;

@Data
@TypeAlias("bond")
@EqualsAndHashCode(callSuper = true)
public class Bond extends Instrument {

    private BondStatic staticData;
    private BondRecommendation recommendation;
    private Rating rating;

    public Bond() {
        this.setType(InstrumentType.BOND);
    }

    public Bond(
            Instrument instrument,
            BondStatic staticData,
            Rating rating
    ) {
        super(instrument);
        this.staticData = staticData;
        this.rating = rating;
    }
}
