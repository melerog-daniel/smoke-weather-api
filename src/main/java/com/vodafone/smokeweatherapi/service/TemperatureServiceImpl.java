package com.vodafone.smokeweatherapi.service;

import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequest;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponse;
import com.vodafone.smokeweatherapi.entity.Temperature;
import com.vodafone.smokeweatherapi.mapper.TemperatureMapper;
import com.vodafone.smokeweatherapi.repository.TemperatureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "TemperatureService")
@RequiredArgsConstructor
@Service
public class TemperatureServiceImpl implements TemperatureService {

    private final TemperatureRepository temperatureRepository;

    public TemperatureResponse getTemperature(final CoordinatesRequest request) {
        Temperature temperature = new Temperature();
        temperature.setTemperatureValue(12);
        Temperature dbResponse = temperatureRepository.save(temperature);
        return TemperatureMapper.fromEntityToResponse(dbResponse);
    }
}
