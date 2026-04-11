package com.dinno.Auth.application.service;

import com.dinno.Auth.application.exception.InvalidCredentialsException;
import com.dinno.Auth.domain.model.AuthResult;
import com.dinno.Auth.domain.port.in.RefreshTokenUseCase;
import com.dinno.Auth.domain.port.out.TokenGeneratorPort;
import com.dinno.Auth.domain.port.out.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenUseCase {

    private final UserRepository userRepository;
    private final TokenGeneratorPort tokenGenerator;

    @Override
    public Mono<AuthResult> refresh(String refreshToken) {
        return Mono.defer(() -> {
            if (!tokenGenerator.validateToken(refreshToken)) {
                log.warn("Refresh failed: Invalid or expired refresh token");
                return Mono.error(new InvalidCredentialsException("Invalid refresh token"));
            }

            try {
                Claims claims = tokenGenerator.getClaims(refreshToken);
                String email = claims.get("email", String.class);

                return userRepository.findByEmail(email)
                        .switchIfEmpty(Mono.error(new InvalidCredentialsException("User not found for refresh token")))
                        .flatMap(user -> {
                            log.info("Token refresh successful for user: {}", email);
                            return tokenGenerator.generateTokens(user);
                        });
            } catch (Exception e) {
                log.error("Error processing refresh token", e);
                return Mono.error(new InvalidCredentialsException("Error processing refresh token"));
            }
        });
    }
}
