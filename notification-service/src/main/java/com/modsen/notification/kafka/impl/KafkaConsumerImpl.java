package com.modsen.notification.kafka.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.notification.dto.RideDto;
import com.modsen.notification.exception.CustomJsonProcessingException;
import com.modsen.notification.kafka.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.modsen.notification.service.NotificationService;

@Service
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @KafkaListener(topics = "driver-request-ride")
    public void listenDriverRequestRide(JsonNode message) {
        RideDto rideDto;
        try {
            rideDto = objectMapper.treeToValue(message, RideDto.class);
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Failed to process JSON for ride request", e);
        }

        notificationService.notifyDriver(rideDto, "driver-request-ride");
    }

    @Override
    @KafkaListener(topics = "start-ride-confirmation")
    public void listenStartRideConfirmation(JsonNode message) {
        RideDto rideDto;
        try {
            rideDto = objectMapper.treeToValue(message, RideDto.class);
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Failed to process JSON for start ride confirmation", e);
        }
        notificationService.notifyDriver(rideDto, "start-ride-confirmation");
    }

    @Override
    @KafkaListener(topics = "end-ride-confirmation")
    public void listenEndRideConfirmation(JsonNode message) {
        RideDto rideDto;
        try {
            rideDto = objectMapper.treeToValue(message, RideDto.class);
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Failed to process JSON for end ride confirmation", e);
        }
        notificationService.notifyDriver(rideDto, "end-ride-confirmation");
    }
}

