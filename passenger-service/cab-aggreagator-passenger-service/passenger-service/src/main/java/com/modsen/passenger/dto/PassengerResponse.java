package com.modsen.passenger.dto;

import java.math.BigDecimal;

public record PassengerResponse(
        Long id,
        String name,
        String email,
        String startPoint,
        String finalPoint,
        String status,
        BigDecimal rating
) {
}