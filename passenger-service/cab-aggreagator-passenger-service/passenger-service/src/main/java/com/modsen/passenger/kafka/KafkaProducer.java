package com.modsen.passenger.kafka;

import com.modsen.passenger.dto.PassengerIdDto;
import com.modsen.passenger.dto.PassengerResponse;
import com.modsen.passenger.dto.RideDto;

public interface KafkaProducer {
    void sendNewPassenger(PassengerResponse passenger);
    void sendRequestRides(PassengerIdDto passengerIdDto);
    void sendUpdateRideRating(RideDto rideDto);
}