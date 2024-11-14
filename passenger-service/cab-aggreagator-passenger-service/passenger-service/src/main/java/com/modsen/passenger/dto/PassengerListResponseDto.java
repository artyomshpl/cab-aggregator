package com.modsen.passenger.dto;

import java.util.List;

public record PassengerListResponseDto(
        List<PassengerResponseDto> passengers
) {
}
