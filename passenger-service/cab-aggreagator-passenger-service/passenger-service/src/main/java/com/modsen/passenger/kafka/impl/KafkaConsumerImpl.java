package com.modsen.passenger.kafka.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.passenger.dto.PassengerResponse;
import com.modsen.passenger.exception.CustomJsonProcessingException;
import com.modsen.passenger.kafka.KafkaConsumer;
import com.modsen.passenger.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {
    private final PassengerService passengerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @KafkaListener(topics = "passenger-updates")
    public void listenPassengerUpdates(JsonNode message) {
        PassengerResponse passenger;
        try {
            passenger = objectMapper.treeToValue(message, PassengerResponse.class);
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Failed to process JSON for passenger updates", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON for passenger updates", e);
        }
        passengerService.updatePassenger(passenger);
    }

}