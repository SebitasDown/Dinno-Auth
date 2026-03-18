package com.dinno.Auth.infrastructure.port.in.web.mapper;

import com.dinno.Auth.domain.model.AuthUser;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.RegisterResponseDto;
import com.dinno.Auth.infrastructure.port.out.persistence.entity.AuthUserEntity;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    RegisterResponseDto toResponse(AuthUser user);
}
