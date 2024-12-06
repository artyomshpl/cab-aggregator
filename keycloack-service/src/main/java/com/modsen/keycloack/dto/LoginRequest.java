package com.modsen.keycloack.dto;

public record LoginRequest(
        String username,
        String password
) {
}
