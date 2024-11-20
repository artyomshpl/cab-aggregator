package com.modsen.rides.kafka;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;

import java.util.List;

public interface KafkaConsumer {
    void listenSendFreeDrivers(List<DriverDto> message);
    void listenNewPassenger(PassengerDto message);
}
