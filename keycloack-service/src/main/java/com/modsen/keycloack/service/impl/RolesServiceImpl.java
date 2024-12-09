package com.modsen.keycloack.service.impl;

import com.modsen.keycloack.dto.RolesRequest;
import com.modsen.keycloack.dto.RolesResponse;
import com.modsen.keycloack.service.RolesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class RolesServiceImpl implements RolesService {
    private final ReactiveJwtDecoder jwtDecoder;

    public RolesServiceImpl(@Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}") String issuerUri) {
        this.jwtDecoder = ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Override
    public Mono<RolesResponse> getRoles(RolesRequest rolesRequest) {
        return jwtDecoder.decode(rolesRequest.token())
                .map(this::extractRoles)
                .map(roles -> new RolesResponse(roles))
                .onErrorResume(ex -> Mono.error(new RuntimeException("Failed to decode JWT or extract roles: " + ex.getMessage())));
    }

    private List<String> extractRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null) {
            return List.of();
        }
        try {
            return (List<String>) realmAccess.get("roles");
        } catch (ClassCastException ex) {
            throw new RuntimeException("Failed to extract roles from JWT: " + ex.getMessage(), ex);
        }
    }
}
