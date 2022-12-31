package com.example.iot.server.common.util;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author xupeng
 * @create 2022/12/5 20:38
 * @description
 */
@Slf4j
public class IpUtil {

    /**
     * 获取客户端ip
     * @param ctx
     * @return
     */
    public static String getClientIp(ChannelHandlerContext ctx) {
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        return ipSocket.getAddress().getHostAddress();
    }

    /**
     * 获取本机IP
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取本机ip异常", e);
        }

        return null;
    }
}
