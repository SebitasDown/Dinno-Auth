package com.dinno.Auth.domain.port.in;

import com.dinno.Auth.domain.model.AuthResult;
import reactor.core.publisher.Mono;

public interface LoginUseCase {
    Mono<AuthResult> login(LoginCommand command);
}
