package com.modsen.rides.kafka.impl;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.dto.RideDto;
import com.modsen.rides.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTemplate<String, List<RideDto>> KafkaTemplateList;

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

    public void sendRides(List<RideDto> message) {
        KafkaTemplateList.send("send-rides", message);
    }
}
