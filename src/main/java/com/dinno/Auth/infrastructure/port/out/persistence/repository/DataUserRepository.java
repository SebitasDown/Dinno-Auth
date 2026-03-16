package com.dinno.Auth.infrastructure.port.out.persistence.repository;

import com.dinno.Auth.infrastructure.port.out.persistence.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface DataUserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

    Mono<UserEntity> findByEmail(String email);
    Mono<UserEntity> findByUsername(String username);
}
