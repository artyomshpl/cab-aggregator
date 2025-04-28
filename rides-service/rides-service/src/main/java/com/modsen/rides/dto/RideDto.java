package com.modsen.rides.dto;

import java.math.BigDecimal;

public record RideDto(
        Long id,
        String passengerId,
        String driverId,
        Long waitTime,
        Long travelTime,
        Double routeLength,
        BigDecimal price,
        Integer rating
) {
}
