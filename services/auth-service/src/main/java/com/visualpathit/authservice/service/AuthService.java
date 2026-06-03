package com.visualpathit.authservice.service;

import com.visualpathit.authservice.client.UserServiceClient;
import com.visualpathit.common.dto.AuthResponse;
import com.visualpathit.common.dto.LoginRequest;
import com.visualpathit.common.dto.RegisterUserRequest;
import com.visualpathit.common.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    public AuthService(UserServiceClient userServiceClient, JwtService jwtService) {
        this.userServiceClient = userServiceClient;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterUserRequest request) {
        UserDto user = userServiceClient.register(request);
        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        userServiceClient.authenticate(request);
        String token = jwtService.generateToken(request.getUsername());
        return new AuthResponse(token, request.getUsername());
    }
}
