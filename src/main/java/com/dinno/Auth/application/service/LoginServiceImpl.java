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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordHasherPort passwordHasher;
    private final TokenGeneratorPort tokenGenerator;

    @Override
    public Mono<AuthResult> login(LoginCommand command) {
        return userRepository.findByEmail(command.email())
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("Invalid email or password")))
                .flatMap(user -> {
                    if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
                        return Mono.error(new InvalidCredentialsException("Invalid email or password"));
                    }
                    if (!user.isActive()) {
                        return Mono.error(new UserInactiveException("User is inactive"));
                    }
                    return tokenGenerator.generateTokens(user);
                });
    }
}
