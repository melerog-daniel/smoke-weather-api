package com.vodafone.smokeweatherapi.service;

import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequest;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponse;

public interface TemperatureService {
    TemperatureResponse getTemperature(final CoordinatesRequest request);
}
