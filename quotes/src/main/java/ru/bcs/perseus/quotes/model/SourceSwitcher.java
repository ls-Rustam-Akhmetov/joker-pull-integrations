package ru.bcs.perseus.quotes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SourceSwitcher {

  @Id
  private String id;
  private Source source;

  public enum Source {
    WITH_DWH, WITHOUT_DWH
  }
}
