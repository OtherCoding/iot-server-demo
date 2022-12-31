package com.example.iot.server.common.util;

import com.example.iot.server.core.protocol.TcpMsgProtocol;
import io.netty.channel.Channel;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author xupeng
 * @create 2022/12/1 13:41
 * @description 发送
 */
public class SendUtil {

    /**
     * 发消息
     * @param channel
     * @param cmd
     * @param bizLine
     * @param message
     */
    public static void sendMsg(Channel channel, short cmd, byte type, byte bizLine , String message) {
        TcpMsgProtocol protocol = new TcpMsgProtocol();
        protocol.setCmd(cmd);
        protocol.setType(type);
        protocol.setBizLine(bizLine);
        protocol.setMsgId(IDGenerator.nextId());

        int contentLen = StringUtils.isEmpty(message) ? 0 : message.getBytes(StandardCharsets.UTF_8).length;
        protocol.setContentLen(contentLen);
        if (contentLen > 0) {
            protocol.setContent(message.getBytes(StandardCharsets.UTF_8));
        }

        channel.writeAndFlush(protocol);
    }
}
