package com.vodafone.smokeweatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SmokeWeatherApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmokeWeatherApiApplication.class, args);
    }

}
