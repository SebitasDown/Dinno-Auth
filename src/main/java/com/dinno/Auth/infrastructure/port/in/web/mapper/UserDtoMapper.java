package com.dinno.Auth.infrastructure.port.in.web.mapper;

import com.dinno.Auth.domain.model.User;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.RegisterResponseDto;
import com.dinno.Auth.infrastructure.port.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(source = "passwordHash", target = "password")
    UserEntity toEntity(User user);

    @Mapping(source = "password", target = "passwordHash")
    User toDomain(UserEntity userEntity);

    RegisterResponseDto toResponse(User user);
}
