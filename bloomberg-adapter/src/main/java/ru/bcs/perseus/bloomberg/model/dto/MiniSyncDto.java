package ru.bcs.perseus.bloomberg.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.bcs.perseus.bloomberg.model.Exchange;
import ru.bcs.perseus.bloomberg.model.db.Sync;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiniSyncDto {

  private String isin;
  private Exchange exchange;

  public Sync convert() {
    return new Sync(this.isin, this.exchange);
  }
}
