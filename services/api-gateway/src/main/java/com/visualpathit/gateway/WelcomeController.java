package com.visualpathit.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public Map<String, Object> welcome() {
        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("health", "/actuator/health");
        endpoints.put("users", "/api/users");
        endpoints.put("auth_register", "POST /api/auth/register");
        endpoints.put("auth_login", "POST /api/auth/login");
        endpoints.put("messages_health", "/api/messages/health");
        endpoints.put("search_index", "POST /api/search/index");
        endpoints.put("file_upload", "POST /api/files/upload");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("service", "vprofile-api-gateway");
        body.put("message", "Use the endpoints below (do not open / in the browser expecting a UI).");
        body.put("endpoints", endpoints);
        return body;
    }
}
