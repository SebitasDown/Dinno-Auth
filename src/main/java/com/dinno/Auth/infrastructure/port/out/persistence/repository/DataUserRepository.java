package com.dinno.Auth.infrastructure.port.out.persistence.repository;

import com.dinno.Auth.infrastructure.port.out.persistence.entity.AuthUserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface DataUserRepository extends ReactiveCrudRepository<AuthUserEntity, UUID> {

    Mono<AuthUserEntity> findByEmail(String email);
    Mono<AuthUserEntity> findByUsername(String username);
}
