package com.modsen.rides.kafka.impl;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendNeedFreeDrivers(String message) {
        kafkaTemplate.send("need-free-drivers", message);
    }

    @Override
    public void sendDriverUpdates(DriverDto message) {
        kafkaTemplate.send("driver-updates", message);
    }

    @Override
    public void sendPassengerUpdates(PassengerDto message) {
        kafkaTemplate.send("passenger-updates", message);
    }
}
