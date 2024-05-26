package com.vodafone.smokeweatherapi.service;

import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTests {
    @Mock
    private KafkaTemplate<String, TemperatureResponseDto> kafkaTemplate;
    @InjectMocks
    private ProducerServiceImpl producerServiceImpl;

    @Test
    void testProduceEvent() {

        TemperatureResponseDto temperatureResponseDto = new TemperatureResponseDto();
        temperatureResponseDto.setTemperature(27.15);
        temperatureResponseDto.setLongitude(36.4);
        temperatureResponseDto.setLatitude(51.0);
        
        String topic = "my-Topic";

        ReflectionTestUtils.setField(producerServiceImpl,
                "producerTopic", topic);

        producerServiceImpl.produce(temperatureResponseDto);
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send(topic, temperatureResponseDto);
    }
}
