package com.dinno.Auth.infrastructure.port.out.persistence.mapper;

import com.dinno.Auth.domain.model.AuthUser;
import com.dinno.Auth.infrastructure.port.out.persistence.entity.AuthUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "passwordHash", target = "password")
    @Mapping(source = "active", target = "isActive")
    @Mapping(source = "deleted", target = "isDeleted")
    AuthUserEntity toEntity(AuthUser user);

    @Mapping(source = "password", target = "passwordHash")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "deleted", target = "deleted")
    AuthUser toDomain(AuthUserEntity userEntity);
}
