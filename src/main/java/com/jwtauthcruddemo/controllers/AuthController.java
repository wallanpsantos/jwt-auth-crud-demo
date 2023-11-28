package com.jwtauthcruddemo.controllers;

import com.jwtauthcruddemo.dtos.input.CredentialsDto;
import com.jwtauthcruddemo.dtos.output.UserDto;
import com.jwtauthcruddemo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/login")
public class AuthController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto dto = userService.login(credentialsDto);
        return ResponseEntity.ok(dto);
    }
}
