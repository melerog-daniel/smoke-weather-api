package com.vodafone.smokeweatherapi.controller;

import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequestDto;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;
import com.vodafone.smokeweatherapi.service.TemperatureService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get temperature by latitude and longitude",
            description = "Get a temperature from database providing latitude and longitude. " +
                    "If cannot find the temperature in database or it's 1 min old, then retrieve data from open-meteo-api and update database.")
    public TemperatureResponseDto getTemperature(CoordinatesRequestDto coordinates) {
        log.info("Get temperature, latitude: {}, longitude: {}", coordinates.getLatitude(), coordinates.getLongitude());
        return temperatureService.getTemperature(coordinates);
    }

    @DeleteMapping
    @Operation(summary = "Delete temperature by latitude and longitude",
            description = "Delete a temperature from database providing latitude and longitude. " +
                    "Returns true if the delete has been successful or false if not")
    public boolean deleteTemperature(CoordinatesRequestDto coordinates) {
        log.info("Delete temperature, latitude: {}, longitude: {}", coordinates.getLatitude(), coordinates.getLongitude());
        return temperatureService.deleteTemperature(coordinates);
    }
}
