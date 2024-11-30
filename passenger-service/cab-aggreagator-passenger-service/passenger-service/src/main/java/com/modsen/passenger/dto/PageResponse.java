package com.modsen.passenger.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        List<T> content,
        int totalPages,
        long totalElements
) {
}