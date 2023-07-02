package ru.bcs.perseus.bloomberg.model.instrument;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Rating {

  private String moody;
  private String sp;
  private String fitch;
  private String composite;
}
