package com.vodafone.smokeweatherapi.service;

import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequestDto;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;

public interface TemperatureService {
    TemperatureResponseDto getTemperature(CoordinatesRequestDto request);

    boolean deleteTemperature(CoordinatesRequestDto request);
}
