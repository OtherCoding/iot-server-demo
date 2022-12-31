package com.example.iot.server.core.codec;

import com.example.iot.server.core.protocol.Protocol;
import com.example.iot.server.core.protocol.TcpMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xupeng
 * @create 2022/11/11 10:54
 * @description 协议编码器
 */
public class ProtocolEncoder extends MessageToByteEncoder<Protocol> {
    private static final Logger logger = LoggerFactory.getLogger("TcpRemoting");

    @Override
    protected void encode(ChannelHandlerContext ctx, Protocol msg, ByteBuf out) throws Exception {
        try {
            if (msg instanceof TcpMsgProtocol) {
                /**
                 * 2B magic
                 * 1B v
                 * 2B cmd
                 * 1B type
                 * 1B bizLine
                 * 4B msgId
                 * 4B contentLen
                 * ?B content
                 */
                TcpMsgProtocol cmd = (TcpMsgProtocol) msg;

                Attribute<Byte> version = ctx.channel().attr(AttributeKey.valueOf("v"));
                byte ver = version == null || version.get() == null ? TcpMsgProtocol.PROTOCOL_VERSION_1 : version.get();
                // 写入magic
                out.writeBytes(TcpMsgProtocol.MAGIC_CODE_BYTES);
                // 写入版本
                out.writeByte(ver);
                // 写入cmd
                out.writeShort(cmd.getCmd());
                // 写入type
                out.writeByte(cmd.getType());
                // 写入bizLine
                out.writeByte(cmd.getBizLine());
                // 写入msgId
                out.writeInt(cmd.getMsgId());
                // 写入contentLen
                out.writeInt(cmd.getContentLen());
                // 写入内容
                if (cmd.getContentLen() > 0) {
                    out.writeBytes(cmd.getContent());
                }
            } else {
                // 抛出异常
                logger.warn("msg type [" + msg.getClass() + "] is not subclass of TcpMsgProtocol");
            }
        } catch (Exception e) {
            logger.error("Exception caught!", e);
            throw e;
        }
    }
}
