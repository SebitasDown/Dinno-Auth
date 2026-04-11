package com.dinno.Auth.domain.port.in;

import com.dinno.Auth.domain.model.AuthResult;
import reactor.core.publisher.Mono;

public interface RefreshTokenUseCase {
    Mono<AuthResult> refresh(String refreshToken);
}
