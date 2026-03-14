package com.dinno.Auth.domain.port.in.register;

import com.dinno.Auth.domain.model.User;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {
    Mono<User> register(RegisterCommand registerCommand);
}
