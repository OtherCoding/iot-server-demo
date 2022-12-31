package com.example.iot.server.service;

import com.example.iot.server.core.handle.CommandProcessor;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;

/**
 * @author xupeng
 * @create 2022/11/30 14:56
 * @description 打开空调处理器
 */
public class OpenAirContionerProcessor implements CommandProcessor {
   @Override
    public void handleCommand(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

//    @Override
//    public void registerProcessor(CommandCode cmd, RemotingProcessor<?> processor) {
//
//    }
//
    @Override
    public void registerDefaultExecutor(ExecutorService executor) {

    }

    @Override
    public ExecutorService getDefaultExecutor() {
        return null;
    }
}
