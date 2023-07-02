package ru.example.instruments.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public
class FieldDefinitionWrapperDto {
    private List<FieldDefinitionDto> fields;
}
