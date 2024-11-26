package com.modsen.rides.dto;

public record DriverDto(
        String id,
        String name,
        String licenseNumber,
        String phoneNumber,
        String location,
        String rideStatus,
        String activityState
) {
}
