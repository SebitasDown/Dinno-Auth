package com.dinno.Auth.infrastructure.port.out.persistence.mapper;

import com.dinno.Auth.domain.model.User;
import com.dinno.Auth.infrastructure.port.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "passwordHash", target = "password")
    @Mapping(source = "active", target = "isActive")
    @Mapping(source = "deleted", target = "isDeleted")
    UserEntity toEntity(User user);

    @Mapping(source = "password", target = "passwordHash")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "deleted", target = "deleted")
    User toDomain(UserEntity userEntity);
}
