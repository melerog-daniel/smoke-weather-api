package com.vodafone.smokeweatherapi.controller;

import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequestDto;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;
import com.vodafone.smokeweatherapi.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "TemperatureController") //Lombok logger
@RequiredArgsConstructor
@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    private final TemperatureService temperatureService;

    @GetMapping
    public TemperatureResponseDto getTemperature(CoordinatesRequestDto coordinates) {
        log.info("Get temperature, latitude: {}, longitude: {}", coordinates.getLatitude(), coordinates.getLongitude());
        return temperatureService.getTemperature(coordinates);
    }

    @DeleteMapping
    public boolean deleteTemperature(CoordinatesRequestDto coordinates) {
        log.info("Delete temperature, latitude: {}, longitude: {}", coordinates.getLatitude(), coordinates.getLongitude());
        return temperatureService.deleteTemperature(coordinates);
    }
}
