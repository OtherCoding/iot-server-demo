package com.example.iot.server.core.handle;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.server.common.constant.Constants;
import com.example.iot.server.common.util.IDGenerator;
import com.example.iot.server.common.util.IpUtil;
import com.example.iot.server.core.command.CommandCode;
import com.example.iot.server.core.protocol.LoginReq;
import com.example.iot.server.core.protocol.TcpMsgProtocol;
import com.example.iot.server.core.session.Connection;
import com.example.iot.server.core.session.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.netty.channel.ChannelHandler.Sharable;

import javax.annotation.Resource;

/**
 * @author xupeng
 * @create 2022/11/10 15:47
 * @description 连接认证处理器
 */
@Slf4j
@Sharable
@Component
public class ConnHandler extends SimpleChannelInboundHandler<TcpMsgProtocol> {

    @Resource
    private ConnectionManager connectionManager;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, TcpMsgProtocol protocol) {
        if (CommandCode.CONNECT_REQ_VALUE == protocol.getCmd()) {
            // 处理连接请求
            handleConnAuthReq(ctx, protocol);
            return;
        }

        // last.传递到下一层handle
        ctx.fireChannelRead(protocol);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String clientId = ctx.channel().attr(Constants.CLIENT_ID_ATTR).get();
        if (StringUtils.isEmpty(clientId)) {
            // 走到这里说明没有经过连接auth
            return;
        }

        connectionManager.removeConnection(clientId);
        log.info("关闭连接 clientId:{}", clientId);
    }

    private void handleConnAuthReq(ChannelHandlerContext ctx, TcpMsgProtocol protocol) {
        // 校验连接
        byte[] content = protocol.getContent();
        LoginReq loginReq = JSONObject.parseObject(new String(content), LoginReq.class);
        if (loginReq == null || StringUtils.isEmpty(loginReq.getClientId())) {
            ctx.channel().close();
            return;
        }

        // 发起连接响应
        connectionManager.addConnection(loginReq.getClientId(), new Connection(ctx.channel(), IpUtil.getClientIp(ctx)));
        ctx.channel().attr(Constants.CLIENT_ID_ATTR).set(loginReq.getClientId());
        ctx.writeAndFlush(createConnResp(protocol));
    }

    private TcpMsgProtocol createConnResp(TcpMsgProtocol protocol) {
        TcpMsgProtocol connectResp = new TcpMsgProtocol();
        connectResp.setCmd(CommandCode.CONNECT_RESP_VALUE);
        connectResp.setBizLine(protocol.getBizLine());
        connectResp.setType(protocol.getType());
        connectResp.setMsgId(IDGenerator.nextId());
        return connectResp;
    }
}
