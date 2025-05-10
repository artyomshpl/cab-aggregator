package com.modsen.passenger.dto;

import java.util.List;

public record PassengerListResponse(
        List<PassengerResponse> passengers
) {
}
