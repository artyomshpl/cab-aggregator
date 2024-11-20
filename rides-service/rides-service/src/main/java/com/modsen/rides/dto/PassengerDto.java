package com.modsen.rides.dto;

public record PassengerDto(
   Long id,
   String name,
   String email,
   String startPoint,
   String finalPoint,
   String status
) {
}
