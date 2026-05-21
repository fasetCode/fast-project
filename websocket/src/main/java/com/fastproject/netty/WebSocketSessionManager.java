package com.fastproject.netty;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 会话管理器
 * 用于管理在线用户的 Channel，支持单用户多端登录（可选）
 */
@Slf4j
public class WebSocketSessionManager {

    // 存储在线用户的 Channel，Key 格式：appId:groupId:userId
    private static final Map<String, Channel> USER_CHANNEL_MAP = new ConcurrentHashMap<>();

    /**
     * 生成个人会话 Key
     */
    private static String buildUserKey(String appId, String groupId, String userId) {
        return appId + ":" + groupId + ":" + userId;
    }


    /**
     * 添加会话
     */
    public static void addSession(String appId, String groupId, String userId, Channel channel) {
        if (appId == null || userId == null || groupId == null) return;
        
        // 添加到个人映射
        String userKey = buildUserKey(appId, groupId, userId);
        USER_CHANNEL_MAP.put(userKey, channel);
    }

    /**
     * 移除会话
     */
    public static void removeSession(String appId, String groupId, String userId) {
        if (appId == null || userId == null || groupId == null) return;
        
        // 从个人映射中移除
        String userKey = buildUserKey(appId, groupId, userId);
        USER_CHANNEL_MAP.remove(userKey);


        log.info("用户下线移除会话: {}, 当前总在线人数: {}", userKey, USER_CHANNEL_MAP.size());
    }

    /**
     * 根据 appId, groupId 和 userId 获取 Channel
     */
    public static Channel getChannel(String appId, String groupId, String userId) {
        if (appId == null || userId == null || groupId == null) return null;
        return USER_CHANNEL_MAP.get(buildUserKey(appId, groupId, userId));
    }



}
