package com.visualpathit.common.dto;

public class AuthResponse {

    private String token;
    private String username;
    private String message;

    public AuthResponse() {}

    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public static AuthResponse error(String message) {
        AuthResponse response = new AuthResponse();
        response.message = message;
        return response;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
