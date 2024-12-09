package com.modsen.keycloack.controller;


import com.modsen.keycloack.dto.*;
import com.modsen.keycloack.service.AuthService;
import com.modsen.keycloack.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RolesService rolesService;

    @PostMapping("/login")
    public Mono<ResponseEntity<TokenResponse>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<TokenResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/roles")
    public Mono<ResponseEntity<RolesResponse>> getRoles(@RequestBody RolesRequest jwtTokenRequest) {
        return rolesService.getRoles(jwtTokenRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}