package com.modsen.passenger.dto;

public record PassengerResponse(
        Long id,
        String name,
        String email,
        String startPoint,
        String finalPoint,
        String status
) {
}