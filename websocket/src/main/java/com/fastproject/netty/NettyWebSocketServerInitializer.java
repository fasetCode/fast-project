package com.fastproject.netty;

import com.fastproject.service.ApplicationService;
import com.fastproject.utils.utils.JsonUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.http.HttpClient;

/**
 * Netty WebSocket 服务端管道初始化器
 * 配置处理链
 */
public class NettyWebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final String websocketPath;
    private final ApplicationService applicationService;

    public NettyWebSocketServerInitializer(String websocketPath, ApplicationService applicationService ) {
        this.websocketPath = websocketPath;
        this.applicationService = applicationService;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // HTTP 解码器
        // WebSocket 是基于 HTTP 协议进行握手的，因此需要先处理 HTTP 请求
        pipeline.addLast(new HttpServerCodec());

        // 以块方式写的处理器，用于处理大数据流
        pipeline.addLast(new ChunkedWriteHandler());

        // 将 HTTP 消息的多个部分聚合成一条完整消息
        // Netty 对 HTTP 消息默认分块处理，聚合后方便后续业务处理
        pipeline.addLast(new HttpObjectAggregator(8192));

        pipeline.addLast(new WebSocketAuthHandler(applicationService));

        // WebSocket 协议处理器
        // 处理握手、心跳 (Ping/Pong) 以及各种控制帧
        // 会将以指定路径开始的 HTTP 请求升级为 WebSocket 协议
        pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath));

        // 自定义 WebSocket 业务处理器
        pipeline.addLast(new NettyWebSocketHandler());
    }
}
