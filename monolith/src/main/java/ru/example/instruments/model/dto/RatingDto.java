package ru.example.instruments.model.dto;

import lombok.Data;

@Data
public class RatingDto {

    private String moody;
    private String sp;
    private String fitch;
    private String composite;
    private String repo;
}
