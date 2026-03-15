package com.dinno.Auth.application.service;

import com.dinno.Auth.application.exception.AccountDeleteException;
import com.dinno.Auth.application.exception.EmailAlreadyExistException;
import com.dinno.Auth.domain.model.User;
import com.dinno.Auth.domain.port.in.RegisterCommand;
import com.dinno.Auth.domain.port.in.RegisterUserUseCase;
import com.dinno.Auth.domain.port.out.PasswordHasherPort;
import com.dinno.Auth.domain.port.out.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasherPort passwordHasherPort;

    public RegisterUserUseCaseImpl(UserRepository userRepository, PasswordHasherPort passwordHasherPort) {
        this.userRepository = userRepository;
        this.passwordHasherPort = passwordHasherPort;
    }

    // Función para registrar usuarios
    @Override
    @Transactional
    public Mono<User> register(RegisterCommand registerCommand) {
        return userRepository.findByEmail(registerCommand.email())
                .flatMap(existingUser -> {
                    if (existingUser.isDeleted()) {
                        log.warn("Registration failed: Email {} belongs to a deleted account", registerCommand.email());
                        return Mono.error(new AccountDeleteException(
                                "No puedes usar este email: la cuenta asociada está en proceso de eliminación"));
                    }
                    log.warn("Registration failed: Email {} already exists", registerCommand.email());
                    return Mono.error(new EmailAlreadyExistException(
                            "El correo electronico " + registerCommand.email() + " ya se encuentra registrada"));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.info("Registering new user with email: {}", registerCommand.email());
                    return userRepository.save(new User(
                            registerCommand.username(),
                            registerCommand.email(),
                            passwordHasherPort.hash(registerCommand.password())));
                }))
                .cast(User.class);
    }
}
