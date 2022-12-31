package com.example.iot.server.core.codec;

import com.example.iot.server.common.constant.Constants;
import com.example.iot.server.core.protocol.TcpMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author xupeng
 * @create 2022/11/11 10:55
 * @description 协议解码器
 */
public class ProtocolDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger("TcpRemoting");

    private final int less = TcpMsgProtocol.HEADER_LENGTH;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= less) {
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
            byte magic01 = in.readByte();
            byte magic02 = in.readByte();
            if (TcpMsgProtocol.MAGIC_CODE_BYTES[0] != magic01 || TcpMsgProtocol.MAGIC_CODE_BYTES[1] != magic02) {
                throw new IllegalArgumentException("Unknown magic code: " + magic01 + ", " + magic02);
            }

            byte v = in.readByte();
            short cmd = in.readShort();
            byte type = in.readByte();
            byte bizLine = in.readByte();
            int msgId = in.readInt();
            int contentLen = in.readInt();
            byte[] content = null;
            if (contentLen > 0) {
                content = new byte[contentLen];
                in.readBytes(content);
            }

            // 包装数据
            TcpMsgProtocol command = createMsgProtocol(cmd, type, bizLine, msgId, contentLen, content);
            out.add(command);
        }
    }

    public TcpMsgProtocol createMsgProtocol(short cmd, byte type, byte bizLine, int msgId, int contentLen, byte[] content) {
        TcpMsgProtocol command = new TcpMsgProtocol();
        command.setCmd(cmd);
        command.setMsgId(msgId);
        command.setType(type);
        command.setBizLine(bizLine);
        command.setContentLen(contentLen);
        command.setContent(content);

        return command;
    }
}
