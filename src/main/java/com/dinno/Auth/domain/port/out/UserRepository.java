package com.dinno.Auth.domain.port.out;

import com.dinno.Auth.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);
    Mono<User> findByEmail(String email);
    Mono<User> findByUsername(String username);
}
