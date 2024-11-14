package com.modsen.passenger.mapper;


import com.modsen.passenger.dto.PassengerRequestDto;
import com.modsen.passenger.dto.PassengerResponseDto;
import com.modsen.passenger.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    Passenger toEntity(PassengerRequestDto passengerRequestDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    PassengerResponseDto toDto(Passenger passenger);
}


