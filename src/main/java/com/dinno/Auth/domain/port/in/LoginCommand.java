package com.dinno.Auth.domain.port.in;

public record LoginCommand(
        String email,
        String password
) {
}
