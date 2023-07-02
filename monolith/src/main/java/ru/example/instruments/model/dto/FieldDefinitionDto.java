package ru.example.instruments.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class FieldDefinitionDto {

    @NotNull
    private String name;
    @Min(value = 0)
    private Integer index;
    private String title;
    private String description;
}
