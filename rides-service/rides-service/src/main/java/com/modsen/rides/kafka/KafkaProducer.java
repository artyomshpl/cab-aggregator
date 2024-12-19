package com.modsen.rides.kafka;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;

public interface KafkaProducer {
    void sendNeedFreeDrivers(String message);
    void sendDriverUpdates(DriverDto message);
    void sendPassengerUpdates(PassengerDto message);
}
