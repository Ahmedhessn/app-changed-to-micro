package com.visualpathit.userservice.controller;

import com.visualpathit.common.dto.LoginRequest;
import com.visualpathit.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/users/internal")
public class InternalAuthController {

    private final UserService userService;

    public InternalAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public Map<String, Object> authenticate(@Valid @RequestBody LoginRequest request) {
        if (!userService.authenticate(request.getUsername(), request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return Map.of("username", request.getUsername(), "authenticated", true);
    }
}
