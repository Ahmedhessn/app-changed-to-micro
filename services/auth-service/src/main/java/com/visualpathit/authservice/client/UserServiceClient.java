package com.visualpathit.authservice.client;

import com.visualpathit.authservice.config.UserServiceProperties;
import com.visualpathit.common.dto.RegisterUserRequest;
import com.visualpathit.common.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.visualpathit.common.dto.LoginRequest;

@Component
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(UserServiceProperties properties) {
        this.restClient = RestClient.builder()
                .baseUrl(properties.baseUrl())
                .build();
    }

    public UserDto register(RegisterUserRequest request) {
        try {
            return restClient.post()
                    .uri("/api/users")
                    .body(request)
                    .retrieve()
                    .body(UserDto.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "User service unavailable");
        }
    }

    public void authenticate(LoginRequest request) {
        try {
            restClient.post()
                    .uri("/api/users/internal/authenticate")
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (org.springframework.web.client.HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "User service unavailable");
        }
    }
}
