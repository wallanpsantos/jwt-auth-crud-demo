package com.jwtauthcruddemo.controllers;

import com.jwtauthcruddemo.dtos.input.CredentialsDto;
import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/login")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto dto = userService.login(credentialsDto);
        return ResponseEntity.ok(dto);
    }
}
