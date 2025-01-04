package com.modsen.driverservice.kafka.impl;

import com.modsen.driverservice.dto.DriverDTO;
import com.modsen.driverservice.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, List<DriverDTO>> kafkaTemplate;

    @Override
    public void sendFreeDrivers(List<DriverDTO> freeDrivers) {
        kafkaTemplate.send("send-free-drivers", freeDrivers);
    }
}
