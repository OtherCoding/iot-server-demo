package com.example.iot.server.core.handle;

import com.example.iot.server.core.protocol.TcpMsgProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author xupeng
 * @create 2022/12/7 17:41
 * @description 业务处理器
 */
@ChannelHandler.Sharable
public class ServiceHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        TcpMsgProtocol tcpMsgProtocol = (TcpMsgProtocol) msg;

//        CommandHandler commandHandler = CommandHandlerFactory.getHandler(tcpMsgProtocol.getCmd());
//        commandHandler.handleCommand(ctx, tcpMsgProtocol);
    }
}
