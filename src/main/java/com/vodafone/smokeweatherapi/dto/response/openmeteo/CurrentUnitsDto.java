package com.vodafone.smokeweatherapi.dto.response.openmeteo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CurrentUnitsDto {
    private String time;
    private String interval;
    private String temperature2m;
}
