package com.dinno.Auth.application.service;

import com.dinno.Auth.application.exception.InvalidCredentialsException;
import com.dinno.Auth.application.exception.UserInactiveException;
import com.dinno.Auth.domain.model.AuthResult;
import com.dinno.Auth.domain.port.in.LoginCommand;
import com.dinno.Auth.domain.port.in.LoginUseCase;
import com.dinno.Auth.domain.port.out.PasswordHasherPort;
import com.dinno.Auth.domain.port.out.TokenGeneratorPort;
import com.dinno.Auth.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordHasherPort passwordHasher;
    private final TokenGeneratorPort tokenGenerator;

    @Override
    public Mono<AuthResult> login(LoginCommand command) {
        return userRepository.findByEmail(command.identifier())
                .switchIfEmpty(userRepository.findByUsername(command.identifier()))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Login failed: User with identifier {} not found", command.identifier());
                    return Mono.error(new InvalidCredentialsException("Invalid email/username or password"));
                }))
                .flatMap(user -> {
                    if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
                        log.warn("Login failed: Incorrect password for identifier {}", command.identifier());
                        return Mono.error(new InvalidCredentialsException("Invalid email/username or password"));
                    }
                    if (!user.isActive()) {
                        log.warn("Login failed: User {} is inactive", command.identifier());
                        return Mono.error(new UserInactiveException("User is inactive"));
                    }
                    log.info("Login successful for identifier: {}", command.identifier());
                    return tokenGenerator.generateTokens(user);
                });
    }
}
