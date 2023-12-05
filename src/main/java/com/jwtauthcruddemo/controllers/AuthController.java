package com.jwtauthcruddemo.controllers;

import com.jwtauthcruddemo.dtos.input.CredentialsDto;
import com.jwtauthcruddemo.dtos.input.SignUpDto;
import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.register(signUpDto);
        return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
    }
}
