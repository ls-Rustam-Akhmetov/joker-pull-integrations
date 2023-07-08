package ru.example.instruments.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.instruments.mapper.FieldDefinitionMapper;
import ru.example.instruments.model.FieldDefinition;
import ru.example.instruments.model.dto.FieldDefinitionDto;
import ru.example.instruments.model.dto.FieldDefinitionWrapperDto;
import ru.example.instruments.service.FieldDefinitionService;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instruments/fields/definitions")
public class FieldDefinitionController {

    private final FieldDefinitionService service;

    @GetMapping
    public Set<FieldDefinitionDto> find() {
        return service.findAll()
                .stream().map(FieldDefinitionMapper.INSTANCE::toDto)
                .collect(toSet());
    }

    @PostMapping
    public FieldDefinitionDto save(@RequestBody @Valid FieldDefinitionDto definitionDto) {
        FieldDefinition definition = FieldDefinitionMapper.INSTANCE.fromDto(definitionDto);
        FieldDefinition savedDefinition = service.save(definition);
        return FieldDefinitionMapper.INSTANCE.toDto(savedDefinition);
    }

    @PostMapping("/batch")
    public List<FieldDefinitionDto> save(@RequestBody @Valid FieldDefinitionWrapperDto definitionsDto) {
        List<FieldDefinition> definitions = FieldDefinitionMapper.INSTANCE.fromDto(definitionsDto.getFields());
        List<FieldDefinition> fieldDefinitions = service.saveAll(definitions);
        return FieldDefinitionMapper.INSTANCE.toDto(fieldDefinitions);
    }

    @PutMapping("/{name}")
    public FieldDefinitionDto update(@PathVariable("name") String name,
                                     @RequestBody @Valid FieldDefinitionDto definitionDto) {
        FieldDefinition definition = FieldDefinitionMapper.INSTANCE.fromDto(definitionDto);
        FieldDefinition updatedDefinition = service.update(name, definition);
        return FieldDefinitionMapper.INSTANCE.toDto(updatedDefinition);
    }
}
