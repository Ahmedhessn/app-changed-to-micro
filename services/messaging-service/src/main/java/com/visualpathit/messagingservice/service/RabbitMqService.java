package com.visualpathit.messagingservice.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.visualpathit.messagingservice.config.RabbitMqProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class RabbitMqService {

    private final RabbitMqProperties properties;

    public RabbitMqService(RabbitMqProperties properties) {
        this.properties = properties;
    }

    public Map<String, Object> health() {
        try (Connection connection = createConnection()) {
            boolean open = connection.isOpen();
            return Map.of("status", open ? "UP" : "DOWN", "host", properties.host());
        } catch (IOException | TimeoutException e) {
            return Map.of("status", "DOWN", "error", e.getMessage());
        }
    }

    public String publish(String message) {
        try (Connection connection = createConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(properties.exchange(), "fanout");
            channel.basicPublish(properties.exchange(), "", null, message.getBytes());
            return "Message published: " + message;
        } catch (IOException | TimeoutException e) {
            return "Failed to publish: " + e.getMessage();
        }
    }

    private Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.host());
        factory.setPort(properties.port());
        factory.setUsername(properties.username());
        factory.setPassword(properties.password());
        return factory.newConnection();
    }
}
