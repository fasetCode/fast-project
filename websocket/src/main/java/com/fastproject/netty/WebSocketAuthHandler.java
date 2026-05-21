package com.fastproject.netty;

import com.fastproject.domain.Application;
import com.fastproject.service.ApplicationService;
import com.fastproject.utils.HttpUtils;
import com.fastproject.utils.utils.HttpResult;
import com.fastproject.utils.utils.JsonUtils;
import com.fastproject.utils.vo.ResultVo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WebSocketAuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public static final AttributeKey<String> APP_ID = AttributeKey.valueOf("appId");
    public static final AttributeKey<String> USER_ID = AttributeKey.valueOf("userId");
    public static final AttributeKey<String> GROUP_ID = AttributeKey.valueOf("groupId");

    private final ApplicationService applicationService;

    public WebSocketAuthHandler(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        String appId = decoder.parameters().get("appId") != null ? decoder.parameters().get("appId").getFirst() : null;
        String token = decoder.parameters().get("token") != null ? decoder.parameters().get("token").getFirst() : null;
        String userId = decoder.parameters().get("userId") != null ? decoder.parameters().get("userId").getFirst() : null;
        String groupId = decoder.parameters().get("groupId") != null ? decoder.parameters().get("groupId").getFirst() : "default";

        Application application = applicationService.findByAppId(appId);

        if (application==null) {
            System.out.println("应用不存在");
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
            sendHttpResponse(ctx, request, response);
            return;
        }

        Map<String, Object> query = new HashMap<>();
        query.put("appId", appId);
        query.put("token", token);
        query.put("userId", userId);
        query.put("groupId", groupId);
        query.put("appSecret", application.getAppSecret());
        System.out.println("查询参数: " + query);


        // 回调授权
        System.out.println("回调授权: " + application.getAuthCallback());
        HttpResult postResult = HttpUtils.postJson(application.getAuthCallback(), JsonUtils.toJson(query));
        System.out.println(JsonUtils.toJson(postResult));
        System.out.println("回调授权结果: " + postResult.getBody());

        if (postResult.getCode()!=200) {
            System.out.println("访问异常");
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
            sendHttpResponse(ctx, request, response);
            return;
        }

//        ResultVo<String> resultVo = JsonUtils.fromJson(postResult.getBody(),
//                TypeToken.getParameterized(ResultVo.class, String.class).getType() );
//
//        if (resultVo.getCode() != 200) {
//            System.out.println("授权失败");
//            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
//            sendHttpResponse(ctx, request, response);
//            return;
//        }


//        if (token == null || !checkToken(token)) {
//            System.out.println("WebSocket 认证失败");
//            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
//            sendHttpResponse(ctx, request, response);
//            return;
//        }

        ctx.channel().attr(APP_ID).set(appId);
        ctx.channel().attr(USER_ID).set(userId);
        ctx.channel().attr(GROUP_ID).set(groupId);
        request.setUri(decoder.path());

        ctx.fireChannelRead(request.retain());
    }


    private void sendHttpResponse(ChannelHandlerContext ctx,
                                  FullHttpRequest req,
                                  FullHttpResponse res) {
        ctx.channel().writeAndFlush(res);
        ctx.channel().close();
    }
}
