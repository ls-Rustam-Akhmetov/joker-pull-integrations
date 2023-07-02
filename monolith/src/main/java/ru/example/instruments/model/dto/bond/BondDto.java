package ru.example.instruments.model.dto.bond;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.example.instruments.model.dto.InstrumentDto;
import ru.example.instruments.model.dto.RatingDto;


@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BondDto extends InstrumentDto {

    @NotNull
    @Valid
    private BondStaticDto staticData;
    private BondRecommendationDto recommendation;
    private RatingDto rating;
}
