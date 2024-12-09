package com.modsen.driverservice.kafka;

import com.modsen.driverservice.dto.DriverDTO;

import java.util.List;

public interface KafkaProducer {
    void sendFreeDrivers(List<DriverDTO> freeDrivers);
}
