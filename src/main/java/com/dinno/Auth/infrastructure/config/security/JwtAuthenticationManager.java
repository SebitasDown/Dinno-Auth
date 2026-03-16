package com.dinno.Auth.infrastructure.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication.getCredentials())
                .cast(String.class)
                .filter(jwtProvider::validateToken)
                .map(jwtProvider::getClaims)
                .map(claims -> {
                    String userId = claims.getSubject();
                    // Aquí podrías extraer roles de los claims si los tuvieras.
                    // Por ahora, asignamos un rol por defecto.
                    return new UsernamePasswordAuthenticationToken(
                            UUID.fromString(userId),
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                });
    }
}
