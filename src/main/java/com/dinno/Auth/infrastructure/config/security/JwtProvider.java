package com.dinno.Auth.infrastructure.config.security;

import com.dinno.Auth.domain.model.AuthResult;
import com.dinno.Auth.domain.model.AuthUser;
import com.dinno.Auth.domain.port.out.TokenGeneratorPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider implements TokenGeneratorPort {

    private final SecretKey key;
    private final long expiration;
    private final long refreshExpiration;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.refresh-expiration}") long refreshExpiration
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
    }

    @Override
    public Mono<AuthResult> generateTokens(AuthUser user) {
        return Mono.fromCallable(() -> {
            String accessToken = createToken(user.getId(), user.getEmail(), expiration);
            String refreshToken = createToken(user.getId(), user.getEmail(), refreshExpiration);
            return new AuthResult(accessToken, refreshToken, expiration, user.getId());
        });
    }

    private String createToken(UUID userId, String email, long expirationSeconds) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + (expirationSeconds * 1000));

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
