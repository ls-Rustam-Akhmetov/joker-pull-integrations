package ru.bcs.perseus.bloomberg.model.instrument.equity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;
import ru.bcs.perseus.bloomberg.model.instrument.Instrument;
import ru.bcs.perseus.bloomberg.model.instrument.Rating;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(callSuper = true)
public class Equity extends Instrument {

  private EquityStatic staticData;
  private Rating rating;

  public Equity(
      Instrument instrument,
      EquityStatic staticData,
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
