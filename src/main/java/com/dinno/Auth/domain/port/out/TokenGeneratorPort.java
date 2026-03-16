package com.dinno.Auth.domain.port.out;

import com.dinno.Auth.domain.model.AuthResult;
import com.dinno.Auth.domain.model.User;
import reactor.core.publisher.Mono;

public interface TokenGeneratorPort {
    Mono<AuthResult> generateTokens(User user);
}
