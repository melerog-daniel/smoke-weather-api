package com.vodafone.smokeweatherapi.service;

import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;

public interface ProducerService {
    void produce(TemperatureResponseDto temperatureResponseDto);
}
