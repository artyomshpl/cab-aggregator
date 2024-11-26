package com.modsen.passenger.kafka;

import com.modsen.passenger.dto.PassengerResponse;

public interface KafkaProducer {
    void sendNewPassenger(PassengerResponse passenger);
}