package com.vodafone.smokeweatherapi.dto.response.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CurrentValuesDto {
    private String time;
    private int interval;
    @JsonProperty("temperature_2m")
    private double temperature;
}
