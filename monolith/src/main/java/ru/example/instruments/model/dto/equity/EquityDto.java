package ru.example.instruments.model.dto.equity;

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
public class EquityDto extends InstrumentDto {

    @NotNull
    @Valid
    private EquityStaticDto staticData;
    private EquityRecommendationDto recommendation;
    private RatingDto rating;
}
