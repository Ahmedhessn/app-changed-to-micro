package com.visualpathit.userservice.cache;

import com.visualpathit.userservice.config.AppConfig.MemcachedProperties;
import com.visualpathit.userservice.model.User;
import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

@Service
public class MemcachedService {

    private static final int EXPIRE_SECONDS = 900;
    private final MemcachedProperties properties;

    public MemcachedService(MemcachedProperties properties) {
        this.properties = properties;
    }

    public User get(String key) {
        MemcachedClient client = connect();
        if (client == null) {
            return null;
        }
        try {
            return (User) client.get(key);
        } finally {
            client.shutdown();
        }
    }

    public String set(User user, String key) {
        MemcachedClient client = connect();
        if (client == null) {
            return null;
        }
        try {
            Future<?> future = client.set(key, EXPIRE_SECONDS, user);
            future.get();
            return "Data is From DB and Data Inserted In Cache !!";
        } catch (Exception e) {
            return null;
        } finally {
            client.shutdown();
        }
    }

    private MemcachedClient connect() {
        try {
            if (!properties.activeHost().isBlank() && !properties.activePort().isBlank()) {
                MemcachedClient client = new MemcachedClient(
                        new InetSocketAddress(properties.activeHost(), Integer.parseInt(properties.activePort())));
                if (!client.getStats().isEmpty()) {
                    return client;
                }
                client.shutdown();
            }
        } catch (Exception ignored) {
            // fall through to standby
        }
        try {
            if (!properties.standbyHost().isBlank() && !properties.standbyPort().isBlank()) {
                return new MemcachedClient(
                        new InetSocketAddress(properties.standbyHost(), Integer.parseInt(properties.standbyPort())));
            }
        } catch (Exception ignored) {
            return null;
        }
        return null;
    }
}
