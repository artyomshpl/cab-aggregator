package com.modsen.passenger.dto;

public record PassengerRequest(
        String name,
        String email,
        String startPoint,
        String finalPoint,
        String status
) {
}
