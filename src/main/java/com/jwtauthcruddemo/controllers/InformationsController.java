package com.jwtauthcruddemo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/informations")
public class InformationsController {

    @GetMapping
    public ResponseEntity<List<String>> informations() {
        return ResponseEntity.ok(List.of("Porjeto", "Backend com JWT"));
    }

}
