package com.dinno.Auth.domain.model;

import java.util.UUID;

public record AuthResult(
    String accessToken,
    String refreshToken,
    long expiresIn, // segundos: 900 = 15 min
    UUID userId
) {
}
