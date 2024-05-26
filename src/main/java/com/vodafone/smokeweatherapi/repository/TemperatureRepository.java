package com.vodafone.smokeweatherapi.repository;

import com.vodafone.smokeweatherapi.entity.Temperature;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TemperatureRepository extends MongoRepository<Temperature, String> {
    Optional<Temperature> findTemperatureByLatitudeAndLongitude(double latitude, double longitude);

    long deleteByLatitudeAndLongitude(double latitude, double longitude);
}