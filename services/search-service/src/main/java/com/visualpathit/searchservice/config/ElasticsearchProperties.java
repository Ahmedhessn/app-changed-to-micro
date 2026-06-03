package com.visualpathit.searchservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elasticsearch")
public record ElasticsearchProperties(String host, int port) {}
