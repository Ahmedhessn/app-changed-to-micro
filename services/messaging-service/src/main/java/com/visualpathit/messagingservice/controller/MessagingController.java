package com.visualpathit.messagingservice.controller;

import com.visualpathit.messagingservice.service.RabbitMqService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessagingController {

    private final RabbitMqService rabbitMqService;

    public MessagingController(RabbitMqService rabbitMqService) {
        this.rabbitMqService = rabbitMqService;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return rabbitMqService.health();
    }

    @PostMapping
    public Map<String, String> publish(@RequestBody(required = false) Map<String, String> body) {
        String message = body != null && body.containsKey("message")
                ? body.get("message")
                : "uuid = " + UUID.randomUUID();
        return Map.of("result", rabbitMqService.publish(message));
    }

    @PostMapping("/batch")
    public Map<String, String> publishBatch(@RequestParam(defaultValue = "20") int count) {
        for (int i = 0; i < count; i++) {
            rabbitMqService.publish("uuid = " + UUID.randomUUID());
        }
        return Map.of("result", "Published " + count + " messages");
    }
}
