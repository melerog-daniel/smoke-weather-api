package com.vodafone.smokeweatherapi.dto.response.openmeteo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OpenMeteoTemperatureResponseDto {
    private double latitude;
    private double longitude;
    private double generationtimeMs;
    private int utcOffsetSeconds;
    private String timezone;
    private String timezoneAbbreviation;
    private double elevation;
    private CurrentUnitsDto currentUnits;
    private CurrentValuesDto current;
}
