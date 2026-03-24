package com.bank.bank_backend.controller;

import org.springframework.web.bind.annotation.*;

import com.bank.bank_backend.dto.AuthRequest;
import com.bank.bank_backend.dto.AuthResponse;
import com.bank.bank_backend.dto.RegisterRequest;
import com.bank.bank_backend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        return authService.login(req);
    }
}