package com.modsen.keycloack.service;

import com.modsen.keycloack.dto.LoginRequest;
import com.modsen.keycloack.dto.RefreshTokenRequest;
import com.modsen.keycloack.dto.TokenResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<TokenResponse> login(LoginRequest loginRequest);
    Mono<TokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
