package com.example.iot.server.core.session;

import java.util.List;

/**
 * @author xupeng
 * @create 2022/11/30 16:02
 * @description
 */
public interface ConnectionManager {

    /**
     * 获取单个连接
     * @param clientId
     * @return
     */
    Connection getConnection(String clientId);

    /**
     * 获取机器上全部连接
     */
    List<Connection> getAllConnection();

    /**
     * 添加连接
     */
    void addConnection(String clientId, Connection Connection);

    /**
     * 删除连接
     */
    void removeConnection(String clientId);

    /**
     * 销毁所有连接
     */
    void removeAllConnection();

    /**
     * 获取client所在的服务端host
     */
    String getHost(String clientId);
}
