package com.vodafone.smokeweatherapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.smokeweatherapi.dto.request.CoordinatesRequestDto;
import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;
import com.vodafone.smokeweatherapi.dto.response.openmeteo.CurrentUnitsDto;
import com.vodafone.smokeweatherapi.dto.response.openmeteo.CurrentValuesDto;
import com.vodafone.smokeweatherapi.dto.response.openmeteo.OpenMeteoTemperatureResponseDto;
import com.vodafone.smokeweatherapi.entity.Temperature;
import com.vodafone.smokeweatherapi.env.MicroserviceEnvironment;
import com.vodafone.smokeweatherapi.mapper.TemperatureMapper;
import com.vodafone.smokeweatherapi.repository.TemperatureRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TemperatureServiceTests {
    @Mock
    private TemperatureRepository temperatureRepository;
    @Mock
    private ProducerService producerService;
    @Spy
    private MicroserviceEnvironment environment;
    private TemperatureServiceImpl temperatureService;
    private static MockWebServer mockOpenMeteoWebServer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockOpenMeteoWebServer = new MockWebServer();
        ReflectionTestUtils.setField(environment,
                "openMeteoApiUrl",
                "http://localhost:" + mockOpenMeteoWebServer.getPort());
        temperatureService = new TemperatureServiceImpl(temperatureRepository, producerService, environment);
    }

    @Test
    void testGetTemperatureIsPresentAndUpdateNotRequired() {
        CoordinatesRequestDto coordinatesRequestDto = new CoordinatesRequestDto();
        coordinatesRequestDto.setLatitude(52.5d);
        coordinatesRequestDto.setLongitude(-87.5d);

        Temperature temperature = new Temperature();
        temperature.setLatitude(coordinatesRequestDto.getLatitude());
        temperature.setLongitude(coordinatesRequestDto.getLongitude());
        temperature.setCreatedAt(new Date());
        temperature.setUpdatedAt(new Date());
        temperature.setTemperatureValue(12);

        Mockito.when(temperatureRepository.findTemperatureByLatitudeAndLongitude(
                coordinatesRequestDto.getLatitude(),
                coordinatesRequestDto.getLongitude())).thenReturn(
                Optional.of(temperature));

        TemperatureResponseDto temperatureResponseDto = TemperatureMapper.fromEntityToDto(temperature);
        Assertions.assertEquals(temperatureResponseDto, temperatureService.getTemperature(coordinatesRequestDto));
    }

    public static OpenMeteoTemperatureResponseDto createMockOpenMeteoResponseDto() {
        CurrentUnitsDto currentUnitsDto = new CurrentUnitsDto();
        currentUnitsDto.setTime("2023-05-26T15:00:00Z");
        currentUnitsDto.setInterval("hourly");
        currentUnitsDto.setTemperature2m("°C");

        CurrentValuesDto currentValuesDto = new CurrentValuesDto();
        currentValuesDto.setTime("2023-05-26T15:00:00Z");
        currentValuesDto.setInterval(1);
        currentValuesDto.setTemperature(22.5);

        OpenMeteoTemperatureResponseDto responseDto = new OpenMeteoTemperatureResponseDto();
        responseDto.setLatitude(52.5d);
        responseDto.setLongitude(-87.5d);
        responseDto.setGenerationtimeMs(0.123);
        responseDto.setUtcOffsetSeconds(0);
        responseDto.setTimezone("UTC");
        responseDto.setTimezoneAbbreviation("UTC");
        responseDto.setElevation(10.0);
        responseDto.setCurrentUnits(currentUnitsDto);
        responseDto.setCurrent(currentValuesDto);

        return responseDto;
    }

    @Test
    void testGetTemperatureIsPresentAndUpdateRequired() throws JsonProcessingException {
        CoordinatesRequestDto coordinatesRequestDto = new CoordinatesRequestDto();
        coordinatesRequestDto.setLatitude(52.5d);
        coordinatesRequestDto.setLongitude(-87.5d);

        Temperature temperature = new Temperature();
        temperature.setLatitude(coordinatesRequestDto.getLatitude());
        temperature.setLongitude(coordinatesRequestDto.getLongitude());
        temperature.setCreatedAt(new Date());
        temperature.setUpdatedAt(new GregorianCalendar(2024, Calendar.APRIL, Calendar.SUNDAY).getTime());
        temperature.setTemperatureValue(12);

        Mockito.when(temperatureRepository.findTemperatureByLatitudeAndLongitude(
                coordinatesRequestDto.getLatitude(),
                coordinatesRequestDto.getLongitude())).thenReturn(
                Optional.of(temperature));

        OpenMeteoTemperatureResponseDto openMeteoTemperatureResponseDto = createMockOpenMeteoResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();

        mockOpenMeteoWebServer.enqueue(new MockResponse().setBody(objectMapper.
                        writeValueAsString(openMeteoTemperatureResponseDto)).
                addHeader("Content-Type", "application/json"));

        TemperatureResponseDto oldTemperatureResponseDto = TemperatureMapper.fromEntityToDto(temperature);
        TemperatureResponseDto updatedTemperatureResponseDto = temperatureService.getTemperature(coordinatesRequestDto);

        Assertions.assertEquals(oldTemperatureResponseDto.getLatitude(), updatedTemperatureResponseDto.getLatitude());
        Assertions.assertEquals(oldTemperatureResponseDto.getLongitude(), updatedTemperatureResponseDto.getLongitude());
        Mockito.verify(temperatureRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testGetTemperatureIsNotPresent() throws JsonProcessingException {
        CoordinatesRequestDto coordinatesRequestDto = new CoordinatesRequestDto();
        coordinatesRequestDto.setLatitude(52.5d);
        coordinatesRequestDto.setLongitude(-87.5d);

        Temperature temperature = new Temperature();
        temperature.setLatitude(coordinatesRequestDto.getLatitude());
        temperature.setLongitude(coordinatesRequestDto.getLongitude());
        temperature.setCreatedAt(new Date());
        temperature.setUpdatedAt(new Date());
        temperature.setTemperatureValue(22.5);

        Mockito.when(temperatureRepository.findTemperatureByLatitudeAndLongitude(
                coordinatesRequestDto.getLatitude(),
                coordinatesRequestDto.getLongitude())).thenReturn(
                Optional.empty());

        OpenMeteoTemperatureResponseDto openMeteoTemperatureResponseDto = createMockOpenMeteoResponseDto();

        ObjectMapper objectMapper = new ObjectMapper();

        mockOpenMeteoWebServer.enqueue(new MockResponse().setBody(objectMapper.
                        writeValueAsString(openMeteoTemperatureResponseDto)).
                addHeader("Content-Type", "application/json"));

        TemperatureResponseDto temperatureResponseDto = TemperatureMapper.fromEntityToDto(temperature);

        Assertions.assertEquals(temperatureResponseDto, temperatureService.getTemperature(coordinatesRequestDto));
        Mockito.verify(temperatureRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void testDeleteTemperatureIfIsPresent() {
        CoordinatesRequestDto coordinatesRequestDto = new CoordinatesRequestDto();
        coordinatesRequestDto.setLatitude(52.5d);
        coordinatesRequestDto.setLongitude(-87.5d);

        Mockito.when(temperatureRepository.deleteByLatitudeAndLongitude(
                coordinatesRequestDto.getLatitude(),
                coordinatesRequestDto.getLongitude())).thenReturn(
                1L);
        Assertions.assertTrue(temperatureService.deleteTemperature(coordinatesRequestDto));
    }

    @Test
    void testDeleteTemperatureIfIsNotPresent() {
        CoordinatesRequestDto coordinatesRequestDto = new CoordinatesRequestDto();
        coordinatesRequestDto.setLatitude(52.5d);
        coordinatesRequestDto.setLongitude(-87.5d);

        Mockito.when(temperatureRepository.deleteByLatitudeAndLongitude(
                coordinatesRequestDto.getLatitude(),
                coordinatesRequestDto.getLongitude())).thenReturn(
                0L);
        Assertions.assertFalse(temperatureService.deleteTemperature(coordinatesRequestDto));
    }
}
