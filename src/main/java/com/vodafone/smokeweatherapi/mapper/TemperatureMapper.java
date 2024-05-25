package com.vodafone.smokeweatherapi.mapper;

import com.vodafone.smokeweatherapi.dto.response.TemperatureResponse;
import com.vodafone.smokeweatherapi.entity.Temperature;

public class TemperatureMapper {

    private TemperatureMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TemperatureResponse fromEntityToResponse(final Temperature temperature) {
        TemperatureResponse response = new TemperatureResponse();
        response.setTemperature(temperature.getTemperatureValue());
        return response;
    }
}
