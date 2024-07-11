package com.example.springsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.DTO.LoginRequest;
import com.example.springsecurity.DTO.LoginResponse;
import com.example.springsecurity.services.TokenService;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        var userToken = tokenService.loginService(loginRequest);

        return ResponseEntity.ok(userToken);
    }

}
