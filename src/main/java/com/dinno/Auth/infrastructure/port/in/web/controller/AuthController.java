package com.dinno.Auth.infrastructure.port.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.dinno.Auth.domain.port.in.LoginCommand;
import com.dinno.Auth.domain.port.in.LoginUseCase;
import com.dinno.Auth.domain.port.in.RefreshTokenUseCase;
import com.dinno.Auth.domain.port.in.RegisterCommand;
import com.dinno.Auth.domain.port.in.RegisterUserUseCase;
import com.dinno.Auth.infrastructure.port.in.web.dto.request.RefreshTokenRequest;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.LoginResponseDto;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.RegisterResponseDto;
import com.dinno.Auth.infrastructure.port.in.web.mapper.AuthDtoMapper;
import com.dinno.Auth.infrastructure.port.in.web.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final UserDtoMapper userMapper;
    private final AuthDtoMapper authMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RegisterResponseDto> register(@Valid @RequestBody RegisterCommand command) {
        log.info("Received registration request for email: {}", command.email());
        return registerUserUseCase.register(command)
                .map(userMapper::toResponse);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LoginResponseDto> login(@Valid @RequestBody LoginCommand command) {
        log.info("Received login request for identifier: {}", command.identifier());
        return loginUseCase.login(command)
                .map(authMapper::toLoginResponseDto);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LoginResponseDto> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("Received token refresh request");
        return refreshTokenUseCase.refresh(request.refreshToken())
                .map(authMapper::toLoginResponseDto);
    }
}
