package com.modsen.keycloack.service.impl;

import com.modsen.keycloack.dto.LoginRequest;
import com.modsen.keycloack.dto.RefreshTokenRequest;
import com.modsen.keycloack.dto.TokenResponse;
import com.modsen.keycloack.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id:default-client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret:default-client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri:default-issuer-uri}")
    private String issuerUri;

    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl(issuerUri + "/protocol/openid-connect/token").build();
    }

    @Override
    public Mono<TokenResponse> login(LoginRequest loginRequest) {
        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("username", loginRequest.username())
                        .with("password", loginRequest.password())
                        .with("grant_type", "password"))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new RuntimeException("Login failed: " + errorBody))))
                .bodyToMono(TokenResponse.class);
    }

    @Override
    public Mono<TokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("refresh_token", refreshTokenRequest.refreshToken())
                        .with("grant_type", "refresh_token"))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class).flatMap(errorBody -> Mono.error(new RuntimeException("Token refresh failed: " + errorBody))))
                .bodyToMono(TokenResponse.class);
    }
}