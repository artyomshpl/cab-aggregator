package com.modsen.keycloack.service;

import com.modsen.keycloack.dto.RolesRequest;
import com.modsen.keycloack.dto.RolesResponse;
import reactor.core.publisher.Mono;

public interface RolesService {
    Mono<RolesResponse> getRoles(RolesRequest jwtTokenRequest);
}
