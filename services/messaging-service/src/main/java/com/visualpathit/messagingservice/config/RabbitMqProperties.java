package com.visualpathit.messagingservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitMqProperties(
        String host,
        int port,
        String username,
        String password,
        String exchange
) {}
