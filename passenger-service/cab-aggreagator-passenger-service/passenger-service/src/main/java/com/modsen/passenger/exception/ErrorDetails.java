package com.modsen.passenger.exception;

import lombok.Builder;

import java.util.Date;

@Builder
public record ErrorDetails(
        Date timestamp,
        String message,
        String details
) {
}