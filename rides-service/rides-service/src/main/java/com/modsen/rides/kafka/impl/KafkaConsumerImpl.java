    package com.modsen.rides.kafka.impl;

    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.fasterxml.jackson.databind.type.CollectionType;
    import com.modsen.rides.dto.DriverDto;
    import com.modsen.rides.dto.PassengerDto;
    import com.modsen.rides.dto.RideDto;
    import com.modsen.rides.exception.CustomJsonProcessingException;
    import com.modsen.rides.kafka.KafkaConsumer;
    import com.modsen.rides.service.RideService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.stereotype.Service;

    import java.io.IOException;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class KafkaConsumerImpl implements KafkaConsumer {
        private final RideService rideService;
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        @KafkaListener(topics = "send-free-drivers", groupId = "rides-service-group")
        public void listenSendFreeDrivers(JsonNode message) {
            List<DriverDto> drivers;

            try {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, DriverDto.class);
                drivers = objectMapper.readValue(message.traverse(), listType);
            } catch (JsonProcessingException e) {
                throw new CustomJsonProcessingException("Failed to process JSON for free drivers", e);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON for free drivers", e);
            }

            rideService.processFreeDrivers(drivers);
        }

        @Override
        @KafkaListener(topics = "new-passenger", groupId = "rides-service-group")
        public void listenNewPassenger(JsonNode message) {
            PassengerDto passengerDto;
            try {
                passengerDto = objectMapper.treeToValue(message, PassengerDto.class);
            } catch (JsonProcessingException e) {
                throw new CustomJsonProcessingException("Failed to process JSON for new passenger", e);
            }
            rideService.processNewPassenger(passengerDto);
        }

        @Override
        @KafkaListener(topics = "update-ride-rating", groupId = "rides-service-group")
        public void listenUpdateRideRating(JsonNode message) {
            RideDto rideDto;
            try {
                rideDto = objectMapper.treeToValue(message, RideDto.class);
            } catch (JsonProcessingException e) {
                throw new CustomJsonProcessingException("Failed to process JSON for ride rating update", e);
            }
            rideService.updateRideRating(rideDto);
        }
    }