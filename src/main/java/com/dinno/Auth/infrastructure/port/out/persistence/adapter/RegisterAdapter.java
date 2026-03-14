package com.dinno.Auth.infrastructure.port.out.persistence.adapter;

import com.dinno.Auth.domain.model.User;
import com.dinno.Auth.domain.port.out.UserRepository;
import com.dinno.Auth.infrastructure.port.out.persistence.mapper.UserMapper;
import com.dinno.Auth.infrastructure.port.out.persistence.repository.DataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RegisterAdapter implements UserRepository {

    private final DataUserRepository dataUserRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<User> save(User user) {
        return Mono.just(user)
                .map(userMapper::toEntity)
                .flatMap(dataUserRepository::save)
                .map(userMapper::toDomain);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return dataUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return dataUserRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }
}
