package com.dinno.Auth.domain.port.in.register;

public record RegisterCommand(
        String username,
        String email,
        String password
) {
}
