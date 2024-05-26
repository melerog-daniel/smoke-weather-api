package com.vodafone.smokeweatherapi.dto.response;

import lombok.Data;

@Data
public class TemperatureResponseDto {
    private double temperature;
    private double latitude;
    private double longitude;
}
