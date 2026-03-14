package com.dinno.Auth.infrastructure.port.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {
    private UUID id;
    private String username;
    private String email;
}
