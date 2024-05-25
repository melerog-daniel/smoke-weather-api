package com.vodafone.smokeweatherapi.repository;

import com.vodafone.smokeweatherapi.entity.Temperature;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemperatureRepository extends MongoRepository<Temperature, String> {
}
