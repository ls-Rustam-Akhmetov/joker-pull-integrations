package ru.example.instruments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    private String moody;
    private String sp;
    private String fitch;
    private String composite;
    private String repo;
}
