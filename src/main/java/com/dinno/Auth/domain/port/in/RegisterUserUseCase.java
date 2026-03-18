package com.dinno.Auth.domain.port.in;

import com.dinno.Auth.domain.model.AuthUser;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {
    Mono<AuthUser> register(RegisterCommand registerCommand);
}
