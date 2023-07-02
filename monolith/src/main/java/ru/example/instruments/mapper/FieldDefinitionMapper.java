package ru.example.instruments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.example.instruments.model.FieldDefinition;
import ru.example.instruments.model.dto.FieldDefinitionDto;

import java.util.List;

@Mapper
@SuppressWarnings("squid:S1214")
public interface FieldDefinitionMapper {

    FieldDefinitionMapper INSTANCE = Mappers.getMapper(FieldDefinitionMapper.class);

    FieldDefinitionDto toDto(FieldDefinition definition);

    FieldDefinition fromDto(FieldDefinitionDto dto);

    List<FieldDefinition> fromDto(List<FieldDefinitionDto> dto);

    List<FieldDefinitionDto> toDto(List<FieldDefinition> definitions);
}


