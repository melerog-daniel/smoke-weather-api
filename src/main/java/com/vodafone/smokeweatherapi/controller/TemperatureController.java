package com.vodafone.smokeweatherapi.controller;

import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequest;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponse;
import com.vodafone.smokeweatherapi.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "TemperatureController") //Lombok logger
@RequiredArgsConstructor
@RestController
@RequestMapping("/temperature")
public class TemperatureController {

    private final TemperatureService temperatureService;

    @GetMapping
    public TemperatureResponse getTemperature(@RequestParam double latitude, @RequestParam double longitude) {
        log.info("Get temperature, latitude: {}, longitude: {}", latitude, longitude);
        CoordinatesRequest request = new CoordinatesRequest();
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        return temperatureService.getTemperature(request);
    }

}
