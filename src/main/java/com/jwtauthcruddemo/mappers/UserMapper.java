package com.jwtauthcruddemo.mappers;

import com.jwtauthcruddemo.dtos.input.SignUpDto;
import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserEntity userEntity);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity toUserEntity(SignUpDto signUpDto);
}
