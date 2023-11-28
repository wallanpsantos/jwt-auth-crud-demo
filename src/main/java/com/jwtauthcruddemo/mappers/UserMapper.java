package com.jwtauthcruddemo.mappers;

import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(UserEntity userEntity);
}
