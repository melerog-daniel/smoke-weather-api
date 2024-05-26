package com.vodafone.smokeweatherapi.repository;

import com.vodafone.smokeweatherapi.dto.response.openmeteo.OpenMeteoTemperatureResponseDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "/forecast")
public interface OpenMeteoRepository {

    @GetExchange
    OpenMeteoTemperatureResponseDto getTemperatureByLatitudeAndLongitude(@RequestParam double latitude, @RequestParam double longitude, @RequestParam String current);
}
