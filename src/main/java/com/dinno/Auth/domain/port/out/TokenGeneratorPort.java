package com.dinno.Auth.domain.port.out;

import com.dinno.Auth.domain.model.AuthResult;
import com.dinno.Auth.domain.model.AuthUser;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

public interface TokenGeneratorPort {
    Mono<AuthResult> generateTokens(AuthUser user);
    boolean validateToken(String token);
    Claims getClaims(String token);
}
