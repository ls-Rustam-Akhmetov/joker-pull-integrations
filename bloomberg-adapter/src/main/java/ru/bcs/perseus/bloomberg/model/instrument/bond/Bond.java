package ru.bcs.perseus.bloomberg.model.instrument.bond;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.instrument.Rating;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(callSuper = true)
@NoArgsConstructor
public class Bond extends Instrument {

    private BondStatic staticData;
    private Rating rating;

    public Bond(
            Instrument instrument,
            BondStatic staticData,
            Rating rating
    ) {
        super(instrument);
        this.staticData = staticData;
        this.rating = rating;
    }

    @Override
    public String getIsin() {
        return staticData != null ? staticData.getIsin() : null;
    }
}
