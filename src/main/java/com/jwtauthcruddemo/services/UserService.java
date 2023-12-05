package com.jwtauthcruddemo.services;

import com.jwtauthcruddemo.dtos.input.CredentialsDto;
import com.jwtauthcruddemo.dtos.input.SignUpDto;
import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.entities.UserEntity;
import com.jwtauthcruddemo.exceptions.AppLoginException;
import com.jwtauthcruddemo.mappers.UserMapper;
import com.jwtauthcruddemo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDto login(CredentialsDto credentialsDto) {
        var userEntity = userRepository.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppLoginException("Unknow login", HttpStatus.NOT_FOUND));

        validatePassword(credentialsDto, userEntity);

        return userMapper.toUserDto(userEntity);
    }

    private void validatePassword(final CredentialsDto credentialsDto, final UserEntity userEntity) {
        if (!passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), userEntity.getPassword())) {
            throw new AppLoginException("Invalid password", HttpStatus.BAD_REQUEST);
        }
    }

    public UserDto register(SignUpDto signUpDto) {
        verifyLoginExists(signUpDto);

        var userEntity = userMapper.toUserEntity(signUpDto);

        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));

        return userMapper.toUserDto(userRepository.save(userEntity));

    }

    private void verifyLoginExists(SignUpDto signUpDto) {
        boolean existsUser = userRepository.existsByLogin(signUpDto.login());
        if (existsUser) throw new AppLoginException("Login already exists", HttpStatus.BAD_REQUEST);
    }
}
