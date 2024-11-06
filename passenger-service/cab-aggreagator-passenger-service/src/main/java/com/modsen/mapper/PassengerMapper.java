package com.modsen.mapper;

import com.modsen.dto.PassengerDTO;
import com.modsen.model.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public PassengerDTO toDto(Passenger passenger) {
        PassengerDTO passengerDto = new PassengerDTO();
        passengerDto.setId(passenger.getId());
        passengerDto.setName(passenger.getName());
        passengerDto.setEmail(passenger.getEmail());
        return passengerDto;
    }

    public Passenger toEntity(PassengerDTO passengerDto) {
        Passenger passenger = new Passenger();
        passenger.setId(passengerDto.getId());
        passenger.setName(passengerDto.getName());
        passenger.setEmail(passengerDto.getEmail());
        return passenger;
    }
}