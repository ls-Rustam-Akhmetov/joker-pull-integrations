package ru.bcs.perseus.bloomberg.model.instrument;

import java.util.Map;
import lombok.Data;

@Data
public class InstrumentV2 {

  private String id; // figi
  private Map<String, Object> fields;
}
