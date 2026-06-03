package com.visualpathit.authservice.controller;

import com.visualpathit.authservice.service.AuthService;
import com.visualpathit.common.dto.AuthResponse;
import com.visualpathit.common.dto.LoginRequest;
import com.visualpathit.common.dto.RegisterUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterUserRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
