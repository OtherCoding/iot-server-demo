package com.example.iot.server.core.session;

import com.example.iot.server.common.util.IpUtil;
import io.netty.channel.Channel;
import org.openjsse.sun.net.util.IPAddressUtil;

/**
 * @author xupeng
 * @create 2022/11/30 16:40
 * @description 连接对象
 */
public class Connection {

    private Channel channel;

    /**
     * todo 如果接入redis，向设备下发消息可以根据host是否等于本机，判断是否需要本服务端来下发消息
     * 客户端连接的服务端地址
     */
    private String clientIp;

    /**
     * server端ip
     */
    private String serverIp;

    public Connection() {
    }

    public Connection(Channel channel, String clientIp) {
        this.channel = channel;
        this.clientIp = clientIp;
        this.serverIp = IpUtil.getLocalIp();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
