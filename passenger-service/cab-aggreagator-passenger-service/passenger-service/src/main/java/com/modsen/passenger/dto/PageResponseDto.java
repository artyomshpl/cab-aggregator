package com.modsen.passenger.dto;

import java.util.List;

public record PageResponseDto<T>(
        List<T> content,
        int totalPages,
        long totalElements
) {
}