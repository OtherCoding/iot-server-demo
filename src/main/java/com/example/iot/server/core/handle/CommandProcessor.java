package com.example.iot.server.core.handle;

import com.example.iot.server.core.command.CommandCode;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ExecutorService;

/**
 * @author xupeng
 * @create 2022/11/29 10:29
 * @description
 */
public interface CommandProcessor {
   /**
     * Handle the command.
     *  处理命令
     * @param ctx
     * @param msg
     * @throws Exception
     */
    void handleCommand(ChannelHandlerContext ctx, Object msg) throws Exception;

    /**
     * Register processor for command with specified code.
     * 注册命令特定代码的处理器
     * @param cmd
     * @param processor
     */
//    void registerProcessor(CommandCode cmd, RemotingProcessor<?> processor);

    /**
     * Register default executor for the handler.
     *  注册处理类的默认执行者
     * @param executor
     */
    void registerDefaultExecutor(ExecutorService executor);

    /**
     * Get default executor for the handler.
     * 得到处理类的默认执行者
     */
    ExecutorService getDefaultExecutor();
}
