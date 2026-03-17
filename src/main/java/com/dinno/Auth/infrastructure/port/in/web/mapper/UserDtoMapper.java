package com.dinno.Auth.infrastructure.port.in.web.mapper;

import com.dinno.Auth.domain.model.AuthUser;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.RegisterResponseDto;
import com.dinno.Auth.infrastructure.port.out.persistence.entity.AuthUserEntity;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(source = "passwordHash", target = "password")
    AuthUserEntity toEntity(AuthUser user);

    @Mapping(source = "password", target = "passwordHash")
    AuthUser toDomain(AuthUserEntity userEntity);

    RegisterResponseDto toResponse(AuthUser user);
}
