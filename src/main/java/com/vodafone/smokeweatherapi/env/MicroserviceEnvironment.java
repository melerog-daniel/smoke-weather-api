package com.vodafone.smokeweatherapi.env;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MicroserviceEnvironment {
    @Value("${open-meteo-api.url}")
    private String openMeteoApiUrl;
}
