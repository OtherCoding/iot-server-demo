package com.example.iot.server.core.handle;

import com.example.iot.server.common.util.IDGenerator;
import com.example.iot.server.core.command.CommandCode;
import com.example.iot.server.core.protocol.TcpMsgProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xupeng
 * @create 2022/12/7 17:28
 * @description 心跳响应处理器
 */
@ChannelHandler.Sharable
public class HeartbeatHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // TODO 线程池
        TcpMsgProtocol heartbeatReq = (TcpMsgProtocol) msg;
        if (CommandCode.HEARTBEAT_VALUE == heartbeatReq.getCmd()) {
            ctx.writeAndFlush(createHeartbeatRespBO(heartbeatReq));
            return;
        }

        // 传递到下一层handle
        ctx.fireChannelRead(msg);
    }

    private TcpMsgProtocol createHeartbeatRespBO(TcpMsgProtocol heartbeatReq) {
        TcpMsgProtocol heartbeatResp = new TcpMsgProtocol();
        heartbeatResp.setCmd(CommandCode.HEARTBEAT_VALUE);
        heartbeatResp.setBizLine(heartbeatReq.getBizLine());
        heartbeatResp.setType(heartbeatReq.getType());
        heartbeatResp.setMsgId(IDGenerator.nextId());
        return heartbeatResp;
    }
}
