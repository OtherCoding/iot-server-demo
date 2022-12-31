package com.example.iot.server.core.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xupeng
 * @create 2022/11/30 16:11
 * @description
 */
@Component
@Slf4j
public class ConnectionDefaultManager implements ConnectionManager {

    private static final Map<String, Connection> CLIENT_MAP = new ConcurrentHashMap<>();

    @Override
    public Connection getConnection(String clientId) {
        return CLIENT_MAP.get(clientId);
    }

    @Override
    public List<Connection> getAllConnection() {
        // todo
        return null;
    }

    @Override
    public void addConnection(String clientId, Connection Connection) {
        CLIENT_MAP.put(clientId, Connection);
    }

    @Override
    public void removeConnection(String clientId) {
        CLIENT_MAP.remove(clientId);
    }

    @Override
    public void removeAllConnection() {
        if (CLIENT_MAP.isEmpty()) {
            return;
        }

        CLIENT_MAP.clear();
        log.warn("All and connections have been removed!");
    }

    @Override
    public String getHost(String clientId) {
        Connection conn = CLIENT_MAP.get(clientId);
        if (conn != null) {
            return conn.getClientIp();
        }

        return null;
    }
}
