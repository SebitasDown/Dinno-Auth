package com.dinno.Auth.infrastructure.port.in.web.dto.response;

import java.util.UUID;

public record LoginResponseDto(
        String accessToken,
        String refreshToken,
        long expiresIn,
        UUID userId
) {
}
