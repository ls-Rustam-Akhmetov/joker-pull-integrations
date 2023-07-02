package ru.example.instruments.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.example.instruments.model.InstrumentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentTypeDto {

    private InstrumentType type;

}
