package com.vodafone.smokeweatherapi.service;

import com.vodafone.smokeweatherapi.dto.response.TemperatureResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProducerServiceImpl implements ProducerService {
    private final KafkaTemplate<String, TemperatureResponseDto> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String producerTopic;

    @Override
    public void produce(TemperatureResponseDto temperatureResponseDto) {
        kafkaTemplate.send(producerTopic, temperatureResponseDto);
    }
}
