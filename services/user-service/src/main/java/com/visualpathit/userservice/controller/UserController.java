package com.visualpathit.userservice.controller;

import com.visualpathit.common.dto.RegisterUserRequest;
import com.visualpathit.common.dto.UserDto;
import com.visualpathit.userservice.cache.MemcachedService;
import com.visualpathit.userservice.mapper.UserMapper;
import com.visualpathit.userservice.model.User;
import com.visualpathit.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MemcachedService memcachedService;

    public UserController(UserService userService, MemcachedService memcachedService) {
        this.userService = userService;
        this.memcachedService = memcachedService;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable long id) {
        String cacheKey = String.valueOf(id);
        User cached = memcachedService.get(cacheKey);
        if (cached != null) {
            return ResponseEntity.ok(Map.of(
                    "source", "cache",
                    "user", UserMapper.toDto(cached)
            ));
        }
        UserDto user = userService.findById(id);
        User entity = userService.findEntityByUsername(user.getUsername());
        String cacheResult = memcachedService.set(entity, cacheKey);
        return ResponseEntity.ok(Map.of(
                "source", cacheResult != null ? "database+cache" : "database",
                "cacheMessage", cacheResult != null ? cacheResult : "Memcached connection failure",
                "user", user
        ));
    }

    @GetMapping("/username/{username}")
    public UserDto getByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public UserDto register(@Valid @RequestBody RegisterUserRequest request) {
        return userService.register(request);
    }

    @PutMapping("/{username}")
    public UserDto updateProfile(@PathVariable String username, @RequestBody UserDto dto) {
        return userService.updateProfile(username, dto);
    }
}
