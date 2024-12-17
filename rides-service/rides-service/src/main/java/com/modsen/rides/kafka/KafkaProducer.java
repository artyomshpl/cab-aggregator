package com.modsen.rides.kafka;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.dto.RideDto;

import java.util.List;

public interface KafkaProducer {
    void sendNeedFreeDrivers(String message);
    void sendDriverUpdates(DriverDto message);
    void sendPassengerUpdates(PassengerDto message);
    void sendRides(List<RideDto> message);
}
