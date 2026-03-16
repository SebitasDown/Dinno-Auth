package com.dinno.Auth.domain.port.in;

public record RegisterCommand(
        String username,
        String email,
        String password
) {
}
