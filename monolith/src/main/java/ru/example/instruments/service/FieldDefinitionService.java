package ru.example.instruments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.instruments.model.FieldDefinition;
import ru.example.instruments.model.exception.NotFoundException;
import ru.example.instruments.repository.FieldDefinitionRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class FieldDefinitionService {

    private final FieldDefinitionRepository repository;


    public Set<FieldDefinition> findAll() {
        return new HashSet<>(repository.findAll().stream()
                .collect(toMap(FieldDefinition::getName, Function.identity())).values());
    }

    public FieldDefinition save(FieldDefinition definition) {
        return repository.save(definition);
    }

    public List<FieldDefinition> saveAll(List<FieldDefinition> definition) {
        return repository.saveAll(definition);
    }

    public FieldDefinition update(String name, FieldDefinition definition) {
        FieldDefinition fieldDefinition = repository.findById(name)
                .orElseThrow(() -> new NotFoundException("Can not update field {} because it is not exist"));

        definition.setName(fieldDefinition.getName());

        return repository.save(definition);
    }
}
