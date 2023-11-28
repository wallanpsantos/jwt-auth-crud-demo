package com.jwtauthcruddemo.mappers;

import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "token", ignore = true)
    UserDto toUserDto(UserEntity userEntity);
}
