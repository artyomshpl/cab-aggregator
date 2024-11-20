package com.modsen.rides.kafka.impl;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.kafka.KafkaConsumer;
import com.modsen.rides.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {
    private final RideService rideService;

    @Override
    @KafkaListener(topics = "send-free-drivers", groupId = "rides-service-group")
    public void listenSendFreeDrivers(List<DriverDto> message) {
        rideService.processFreeDrivers(message);
    }

    @Override
    @KafkaListener(topics = "new-passenger", groupId = "rides-service-group")
    public void listenNewPassenger(PassengerDto message) {
        rideService.processNewPassenger(message);
    }
}
