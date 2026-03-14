package com.dinno.Auth.infrastructure.port.in.web.controller;

import com.dinno.Auth.domain.port.in.register.RegisterCommand;
import com.dinno.Auth.domain.port.in.register.RegisterUserUseCase;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.RegisterResponseDto;
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
    private final UserDtoMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RegisterResponseDto> register(@Valid @RequestBody RegisterCommand command) {
        return registerUserUseCase.register(command)
                .map(userMapper::toResponse);
    }
}
