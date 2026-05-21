package com.fastproject.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty WebSocket 业务处理器
 * 负责处理 WebSocket 文本帧
 */
@Slf4j
public class NettyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 当收到新的 WebSocket 文本消息时调用
     * @param ctx 通道处理上下文
     * @param frame WebSocket 文本帧
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String userId = ctx.channel().attr(WebSocketAuthHandler.USER_ID).get();
        String appId = ctx.channel().attr(WebSocketAuthHandler.APP_ID).get();
        String groupId = ctx.channel().attr(WebSocketAuthHandler.GROUP_ID).get();

        String request = frame.text();
        log.info("收到来自 [appId={}, groupId={}, userId={}] 的消息: {}", appId, groupId, userId, request);

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器已收到来自 " + userId + " (群组: " + groupId + ") 的消息: " + request));
    }

    /**
     * 当客户端连接建立时调用
     * @param ctx 通道处理上下文
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            String userId = ctx.channel().attr(WebSocketAuthHandler.USER_ID).get();
            String appId = ctx.channel().attr(WebSocketAuthHandler.APP_ID).get();
            String groupId = ctx.channel().attr(WebSocketAuthHandler.GROUP_ID).get();
            
            // 握手成功后，将 Channel 加入会话管理
            WebSocketSessionManager.addSession(appId, groupId, userId, ctx.channel());
            
            log.info("客户端握手完成: {}, appId: {}, groupId: {}, userId: {}", ctx.channel().id().asLongText(), appId, groupId, userId);
            return;
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 当客户端断开连接时调用
     * @param ctx 通道处理上下文
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String userId = ctx.channel().attr(WebSocketAuthHandler.USER_ID).get();
        String appId = ctx.channel().attr(WebSocketAuthHandler.APP_ID).get();
        String groupId = ctx.channel().attr(WebSocketAuthHandler.GROUP_ID).get();
        
        // 客户端断开连接时，从会话管理中移除
        WebSocketSessionManager.removeSession(appId, groupId, userId);
        
        log.info("客户端已断开: {}, appId: {}, groupId: {}, userId: {}", ctx.channel().id().asLongText(), appId, groupId, userId);
    }

    /**
     * 当发生异常时调用
     * @param ctx 通道处理上下文
     * @param cause 异常原因
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("WebSocket 发生异常", cause);
        
        // 发生异常时，确保连接关闭，handlerRemoved 会被触发并清理会话
        ctx.close();
    }
}
