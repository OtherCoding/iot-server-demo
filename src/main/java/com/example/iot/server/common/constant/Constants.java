package com.example.iot.server.common.constant;

import io.netty.util.AttributeKey;

/**
 * @author xupeng
 * @create 2022/11/9 17:00
 * @description
 */
public class Constants {

    /**
     * cpu 个数
     */
    public static final int CPUS = Runtime.getRuntime().availableProcessors();

    /**
     * 设备id标识
     */
    public static final String CLIENT_ID_KEY = "clientId";
    public static final AttributeKey<String> CLIENT_ID_ATTR = AttributeKey.valueOf(CLIENT_ID_KEY);
}
