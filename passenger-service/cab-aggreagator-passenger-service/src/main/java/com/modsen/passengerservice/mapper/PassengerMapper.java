package com.modsen.passengerservice.mapper;

import com.modsen.passengerservice.dto.PassengerDto;
import com.modsen.passengerservice.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    PassengerDto toDto(Passenger passenger);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    Passenger toEntity(PassengerDto passengerDto);
}
