package com.vodafone.smokeweatherapi.mapper;

import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;
import com.vodafone.smokeweatherapi.entity.Temperature;

public class TemperatureMapper {

    private TemperatureMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TemperatureResponseDto fromEntityToDto(final Temperature temperature) {
        TemperatureResponseDto response = new TemperatureResponseDto();
        response.setTemperature(temperature.getTemperatureValue());
        response.setLatitude(temperature.getLatitude());
        response.setLongitude(temperature.getLongitude());
        return response;
    }
}
