package com.dinno.Auth.domain.port.out;

import com.dinno.Auth.domain.model.AuthUser;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<AuthUser> save(AuthUser user);
    Mono<AuthUser> findByEmail(String email);
    Mono<AuthUser> findByUsername(String username);
}
