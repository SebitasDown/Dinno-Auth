package com.dinno.Auth.infrastructure.port.in.web.mapper;

import com.dinno.Auth.domain.model.AuthResult;
import com.dinno.Auth.infrastructure.port.in.web.dto.response.LoginResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthDtoMapper {

    LoginResponseDto toLoginResponseDto(AuthResult authResult);

}
