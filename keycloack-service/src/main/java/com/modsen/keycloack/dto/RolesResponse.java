package com.modsen.keycloack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RolesResponse(
        @JsonProperty("roles") List<String> roles
) {
}
