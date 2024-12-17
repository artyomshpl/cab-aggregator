package com.modsen.passenger.kafka.impl;

import com.modsen.passenger.dto.PassengerIdDto;
import com.modsen.passenger.dto.PassengerResponse;
import com.modsen.passenger.dto.RideDto;
import com.modsen.passenger.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void sendNewPassenger(PassengerResponse passenger) {
        kafkaTemplate.send("new-passenger", passenger);
    }

    @Override
    public void sendRequestRides(PassengerIdDto passengerIdDto) {
        kafkaTemplate.send("request-rides", passengerIdDto);
    }

    @Override
    public void sendUpdateRideRating(RideDto rideDto) {
        kafkaTemplate.send("update-ride-rating", rideDto);
    }
}
