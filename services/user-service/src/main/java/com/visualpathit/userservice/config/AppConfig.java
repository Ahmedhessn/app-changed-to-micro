package com.visualpathit.userservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public MemcachedProperties memcachedProperties(
            @Value("${memcached.active.host}") String activeHost,
            @Value("${memcached.active.port}") String activePort,
            @Value("${memcached.standby.host}") String standbyHost,
            @Value("${memcached.standby.port}") String standbyPort) {
        return new MemcachedProperties(activeHost, activePort, standbyHost, standbyPort);
    }

    public record MemcachedProperties(
            String activeHost,
            String activePort,
            String standbyHost,
            String standbyPort
    ) {}
}
