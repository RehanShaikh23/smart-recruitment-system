package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.JwtResponse;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.LoginRequest;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.RegisterRequest;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    @Operation(summary = "Register new user")
    public JwtResponse register(@Valid @RequestBody RegisterRequest r) {
        System.out.println("Incoming Request"+r);
        return service.register(r);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public JwtResponse login(@Valid @RequestBody LoginRequest r) {
        return service.login(r);
    }

}
