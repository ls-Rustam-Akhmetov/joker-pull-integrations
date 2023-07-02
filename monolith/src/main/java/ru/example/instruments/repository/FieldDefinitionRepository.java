package ru.example.instruments.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.example.instruments.model.FieldDefinition;

public interface FieldDefinitionRepository extends MongoRepository<FieldDefinition, String> {
}
