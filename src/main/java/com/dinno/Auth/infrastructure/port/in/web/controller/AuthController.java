package com.dinno.Auth.infrastructure.port.in.web.controller;

import com.dinno.Auth.domain.port.in.LoginCommand;
import com.dinno.Auth.domain.port.in.LoginUseCase;
import com.dinno.Auth.domain.port.in.RegisterCommand;
import com.dinno.Auth.domain.port.in.RegisterUserUseCase;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.LoginResponseDto;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.RegisterResponseDto;
import com.dinno.Auth.infrastructure.port.in.web.mapper.AuthDtoMapper;
import com.dinno.Auth.infrastructure.port.in.web.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final UserDtoMapper userMapper;
    private final AuthDtoMapper authMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RegisterResponseDto> register(@Valid @RequestBody RegisterCommand command) {
        return registerUserUseCase.register(command)
                .map(userMapper::toResponse);
    }
    
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LoginResponseDto> login(@Valid @RequestBody LoginCommand command) {
        return loginUseCase.login(command)
                .map(authMapper::toLoginResponseDto);
    }
}
