package ru.example.instruments.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@NoArgsConstructor
public class FieldDefinition {

    @Id
    @NotNull
    private String name;

    @Min(value = 0, message = "Index can not be less then zero")
    private Integer index;
    private String title;
    private String description;
}
