package com.example.iot.server.core;

import com.example.iot.server.common.util.NettyEventLoopUtil;
import com.example.iot.server.core.codec.ProtocolDecoder;
import com.example.iot.server.core.codec.ProtocolEncoder;
import com.example.iot.server.core.handle.ConnHandler;
import com.example.iot.server.core.handle.HeartbeatHandler;
import com.example.iot.server.core.handle.ServiceHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import static com.example.iot.server.common.constant.Constants.CPUS;

/**
 * @author xupeng
 * @create 2022/11/9 10:38
 * @description tcp启动核心
 */
@Component
@Slf4j
public class TcpServer {

    // 可放入配置中心
    private final int tcpPort = 8888;

    private final long idleTime = 14;

    private final EventLoopGroup bossGroup = NettyEventLoopUtil.newEventLoopGroup(1, new DefaultThreadFactory("boss", true));
    private final EventLoopGroup workerGroup = NettyEventLoopUtil.newEventLoopGroup(CPUS + 1, new DefaultThreadFactory("worker", true));

    @Resource
    private ConnHandler connHandler;

    @PostConstruct
    public void initAndStart() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NettyEventLoopUtil.getServerSocketChannelClass())
                    // 如果未设置或所设置的值小于1，Java将使用默认值50；如果accpet queue队列满了，server将发送一个ECONNREFUSED错误信息Connection refused到client
                    .option(ChannelOption.SO_BACKLOG, 4096)
                    // 表示允许重复使用本地地址和端口
                    .option(ChannelOption.SO_REUSEADDR, true)
                    // TCP_NODELAY的值设置为true表示关闭延迟,禁用nagle算法,这个算法会导致相继两次向连接发送请求包,读数据时会有一个最多达500毫秒的延时.
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置write buff高低水位 默认 32 * 1024 ～ 64 * 1024
                    // .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 64 * 1024))
                    // init byte buf allocator
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipe = ch.pipeline();
//                            pipe.addLast(new IdleStateHandler(idleTime, idleTime, idleTime, TimeUnit.SECONDS));
                            pipe.addLast(new ProtocolDecoder());
                            pipe.addLast(new ProtocolEncoder());
                            pipe.addLast("connHandler", connHandler);
                            pipe.addLast("heartbeatHandler", new HeartbeatHandler());
                            pipe.addLast("serviceHandler", new ServiceHandler());
                        }
                    });

            bootstrap.bind(tcpPort).sync();
            log.info("iot server started!");
        } catch (Exception e) {
            log.error("iot服务器启动失败", e);
        }
    }

    @PreDestroy
    public void destroy() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
