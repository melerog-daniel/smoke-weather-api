package com.vodafone.smokeweatherapi.service;

import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequestDto;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;
import com.vodafone.smokeweatherapi.dto.response.openmeteo.OpenMeteoTemperatureResponseDto;
import com.vodafone.smokeweatherapi.entity.Temperature;
import com.vodafone.smokeweatherapi.env.MicroserviceEnvironment;
import com.vodafone.smokeweatherapi.mapper.TemperatureMapper;
import com.vodafone.smokeweatherapi.repository.OpenMeteoRepository;
import com.vodafone.smokeweatherapi.repository.TemperatureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "TemperatureService")
@Service
public class TemperatureServiceImpl implements TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final OpenMeteoRepository openMeteoRepository;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository, MicroserviceEnvironment microserviceEnvironment) {
        this.temperatureRepository = temperatureRepository;
        var restClient = RestClient.builder().baseUrl(microserviceEnvironment.getOpenMeteoApiUrl()).requestInterceptor(((request, body, execution) -> {
            log.info("request {}, method {}", request.getURI(), request.getMethod());
            return execution.execute(request, body);
        })).build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        this.openMeteoRepository = factory.createClient(OpenMeteoRepository.class);
    }

    public TemperatureResponseDto getTemperature(final CoordinatesRequestDto coordinates) {

        var optionalTemperature = temperatureRepository.findTemperatureByLatitudeAndLongitude(coordinates.getLatitude(), coordinates.getLongitude());
        Temperature temperature;

        if (optionalTemperature.isPresent()) {
            temperature = optionalTemperature.get();
            if (isUpdateNeeded(temperature.getUpdatedAt())) {
                var openMeteoTemperature = getOpenMeteoTemperature(coordinates.getLatitude(), coordinates.getLongitude());
                updateTemperature(openMeteoTemperature, temperature);
            }

        } else {
            var openMeteoTemperature = getOpenMeteoTemperature(coordinates.getLatitude(),
                    coordinates.getLongitude());
            temperature = new Temperature();
            temperature.setLatitude(coordinates.getLatitude());
            temperature.setLongitude(coordinates.getLongitude());
            temperature.setTemperatureValue(openMeteoTemperature.getCurrent().getTemperature());
            temperatureRepository.save(temperature);
        }

        return TemperatureMapper.fromEntityToDto(temperature);
    }

    @Override
    public boolean deleteTemperature(final CoordinatesRequestDto coordinates) {
        return temperatureRepository.deleteByLatitudeAndLongitude(coordinates.getLatitude(), coordinates.getLongitude()) > 0;
    }

    private void updateTemperature(OpenMeteoTemperatureResponseDto openMeteoTemperature, Temperature temperature) {
        temperature.setTemperatureValue(openMeteoTemperature.getCurrent().getTemperature());
        temperature.setUpdatedAt(new Date());
        temperatureRepository.save(temperature);
    }

    private OpenMeteoTemperatureResponseDto getOpenMeteoTemperature(double latitude, double longitude) {
        return openMeteoRepository.getTemperatureByLatitudeAndLongitude(latitude, longitude, "temperature_2m");
    }

    private boolean isUpdateNeeded(Date updatedAt) {
        long currentTimeMillis = System.currentTimeMillis();
        long updatedAtMillis = updatedAt.getTime();
        long differenceMillis = currentTimeMillis - updatedAtMillis;
        return TimeUnit.MILLISECONDS.toMinutes(differenceMillis) >= 1;
    }


}
