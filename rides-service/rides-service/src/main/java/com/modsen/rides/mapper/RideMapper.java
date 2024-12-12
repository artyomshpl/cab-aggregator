package com.modsen.rides.mapper;

import com.modsen.rides.dto.RideDto;
import com.modsen.rides.model.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RideMapper {
    @Mapping(source = "passengerId", target = "passengerId")
    @Mapping(source = "driverId", target = "driverId")
    @Mapping(source = "waitTime", target = "waitTime")
    @Mapping(source = "travelTime", target = "travelTime")
    @Mapping(source = "routeLength", target = "routeLength")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "rating", target = "rating")
    Ride toEntity(RideDto rideDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "passengerId", target = "passengerId")
    @Mapping(source = "driverId", target = "driverId")
    @Mapping(source = "waitTime", target = "waitTime")
    @Mapping(source = "travelTime", target = "travelTime")
    @Mapping(source = "routeLength", target = "routeLength")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "rating", target = "rating")
    RideDto toDto(Ride ride);
}
