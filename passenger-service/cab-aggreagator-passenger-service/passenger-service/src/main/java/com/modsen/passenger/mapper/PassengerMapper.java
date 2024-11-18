package com.modsen.passenger.mapper;


import com.modsen.passenger.dto.PassengerRequest;
import com.modsen.passenger.dto.PassengerResponse;
import com.modsen.passenger.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    Passenger toEntity(PassengerRequest passengerRequest);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    PassengerResponse toDto(Passenger passenger);
}


