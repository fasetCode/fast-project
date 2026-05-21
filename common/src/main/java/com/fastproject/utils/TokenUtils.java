package com.fastproject.utils;

import com.fastproject.jedis.JedisTemplate;
import com.fastproject.props.SecurityProps;
import com.fastproject.utils.utils.JsonUtils;
import com.fastproject.vo.SecurityUserVo;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fastproject.vo.SysUsersLoginVo;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {

    private final SecurityProps securityProps;

    private final JedisTemplate jedisTemplate;
    public TokenUtils(SecurityProps securityProps, JedisTemplate jedisTemplate) {
        this.securityProps = securityProps;
        this.jedisTemplate = jedisTemplate;
    }

    // 本地缓存：token -> SecurityUserVo
    private final Cache<String, SecurityUserVo> tokenCache = Caffeine.newBuilder()
            .maximumSize(1_000_000)                // 100万条，适合50万在线用户
            .expireAfterWrite(60, TimeUnit.SECONDS) // 1分钟过期
            .initialCapacity(50_000)               // 初始化容量
            .recordStats()
            .build();



    /**
     * 登录生成 token
     * @param userId 用户ID
     * @param user 用户对象
     * @param permissions 用户权限
     * @return token
     */
    public String loginToken(Long userId, SysUsersLoginVo  user, Set<String> permissions) {

        // 生成 64位 UUID token
        String token = UUID.randomUUID().toString().replaceAll("-", "")
                + UUID.randomUUID().toString().replaceAll("-", "");

        // 获取当前请求信息
        String ip = "";
        String ua = "";
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                if (request != null) {
                    ip = getClientIp(request);
                    ua = request.getHeader("User-Agent");
                    if (ua == null) {
                        ua = "";
                    }
                }
            }
        } catch (Exception e) {
            // 忽略异常，使用默认值
        }

        // 封装完整用户信息
        SecurityUserVo securityVo = new SecurityUserVo();
        securityVo.setUserId(userId);
        securityVo.setTenantId(user.getTenantId());
        securityVo.setUser(user);
        securityVo.setPermissions(permissions);
        securityVo.setLoginTime(System.currentTimeMillis()); // 登录时间戳，用于多设备踢人
        securityVo.setIp(ip);
        securityVo.setUa(ua);
        securityVo.setBrowser(parseBrowser(ua));
        securityVo.setDevice(parseDevice(ua));
        securityVo.setAddress(parseAddress(ip));

        // 存本地缓存
        tokenCache.put(token, securityVo);

        // Redis key：userId + token
        String redisUserKey = securityProps.getCacheKey() + userId + ":" + token;
        String redisTokenKey = securityProps.getCacheKey() + token;

        // 过期时间（秒）
        long expireSeconds = securityProps.getExpire() * 60L;

        String json = JsonUtils.toJson(securityVo);
        // 存 Redis 两份 key
        jedisTemplate.setex(redisUserKey,json , expireSeconds);
        jedisTemplate.setex(redisTokenKey, json, expireSeconds);

        // 处理多设备逻辑
        int deviceLimit = securityProps.getDevice(); // 0 表示不限制
        if (deviceLimit > 0) {
            Set<String> keys = jedisTemplate.keys(securityProps.getCacheKey() + userId + ":*");
            if (keys != null && keys.size() > deviceLimit) {
                // 找到最早登录的 token（按 loginTime）
                String oldestKey = null;
                long oldestTime = Long.MAX_VALUE;
                for (String key : keys) {
                    String obj = jedisTemplate.get(key);
                    if (obj != null) {
                        SecurityUserVo securityUserVo = JsonUtils.fromJson(obj, SecurityUserVo.class);
                        if (securityUserVo.getLoginTime() < oldestTime) {
                            oldestTime = securityUserVo.getLoginTime();
                            oldestKey = key;
                        }
                    }
                }
                if (oldestKey != null) {
                    jedisTemplate.del(oldestKey);
                    String oldToken = oldestKey.substring(oldestKey.lastIndexOf(":") + 1);
                    jedisTemplate.del(securityProps.getCacheKey() + oldToken); // 删除 token 单独存的 key
                    tokenCache.invalidate(oldToken); // 删除本地缓存
                }
            }
        }

        return token;
    }

    /**
     * 校验 token 并续命
     * @return SecurityUserVo 或 null
     */
    public SecurityUserVo getUser() {

        // 获取当前请求
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            return null;
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();
        if (request == null) return null;

        // 从请求头获取 token
        String token = request.getHeader(securityProps.getTokenKey());
        if (token == null) return null;

        if (token.isEmpty()) return null;

        // 优先查本地缓存
        SecurityUserVo securityVo = tokenCache.getIfPresent(token);

        if (securityVo != null) {
            return securityVo;
        }

        // Redis 查找
        String obj = jedisTemplate.get(securityProps.getCacheKey() + token);


        if (obj != null) {
            securityVo = JsonUtils.fromJson(obj, SecurityUserVo.class);
            // 回写本地缓存
            tokenCache.put(token, securityVo);

            // 续命 Redis TTL
            String redisTokenKey = securityProps.getCacheKey() + token;
            String redisUserKey = securityProps.getCacheKey() + securityVo.getUserId() + ":" + token;
            long expireSeconds = securityProps.getExpire() * 60L;

            jedisTemplate.expire(redisTokenKey, expireSeconds);
            jedisTemplate.expire(redisUserKey, expireSeconds);

            return securityVo;
        }
        return null;
    }


    /**
     * 判断当前请求是否已登录
     * @return true 已登录，false 未登录
     */
    public boolean isLogin() {
        // 复用 getUser() 方法获取 SecurityUserVo
        SecurityUserVo securityVo = getUser();
        return securityVo != null;
    }


    /**
     * 注销 token
     * 不需要传入 token，直接从请求头 Authorization 获取
     */
    public void logoutToken() {
        // 获取当前请求
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes servletRequestAttributes)) {
            return;
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();
        if (request == null) return;

        // 从请求头获取 token
        String token = request.getHeader(securityProps.getTokenKey());
        if (token == null) return;


        // 删除本地缓存
        tokenCache.invalidate(token);

        // 从 Redis 获取 token 对应的 SecurityUserVo
        String obj = jedisTemplate.get(securityProps.getCacheKey() + token);
        if (obj != null) {
            SecurityUserVo securityVo = JsonUtils.fromJson(obj, SecurityUserVo.class);
            Object userId = securityVo.getUserId();
            String redisUserKey = securityProps.getCacheKey() + userId + ":" + token;
            String redisTokenKey = securityProps.getCacheKey() + token;

            // 删除 Redis 两份 key
            jedisTemplate.del(redisUserKey);
            jedisTemplate.del(redisTokenKey);
        } else {
            // 万一 Redis 中 token 不存在，也尝试删除 token key（幂等操作）
            jedisTemplate.del(securityProps.getCacheKey() + token);
        }
    }

    /**
     * 获取客户端真实 IP 地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个代理情况，取第一个 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip != null ? ip : "";
    }

    /**
     * 从 User-Agent 解析浏览器信息
     */
    private String parseBrowser(String ua) {
        if (ua == null || ua.isEmpty()) {
            return "未知浏览器";
        }
        String userAgent = ua.toLowerCase();
        if (userAgent.contains("edg")) {
            return "Microsoft Edge";
        } else if (userAgent.contains("chrome")) {
            return "Chrome";
        } else if (userAgent.contains("firefox")) {
            return "Firefox";
        } else if (userAgent.contains("safari") && !userAgent.contains("chrome")) {
            return "Safari";
        } else if (userAgent.contains("opera") || userAgent.contains("opr")) {
            return "Opera";
        } else if (userAgent.contains("trident") || userAgent.contains("msie")) {
            return "Internet Explorer";
        }
        return "未知浏览器";
    }

    /**
     * 从 User-Agent 解析设备信息
     */
    private String parseDevice(String ua) {
        if (ua == null || ua.isEmpty()) {
            return "未知设备";
        }
        String userAgent = ua.toLowerCase();
        if (userAgent.contains("mobile") || userAgent.contains("android") || userAgent.contains("iphone")) {
            if (userAgent.contains("ipad") || userAgent.contains("tablet")) {
                return "平板设备";
            }
            return "移动设备";
        } else if (userAgent.contains("macintosh") || userAgent.contains("mac os")) {
            return "Mac电脑";
        } else if (userAgent.contains("windows")) {
            return "Windows电脑";
        } else if (userAgent.contains("linux")) {
            return "Linux电脑";
        }
        return "未知设备";
    }

    /**
     * 根据 IP 解析地址（简单实现，实际需要调用 IP 地址库）
     */
    private String parseAddress(String ip) {
        if (ip == null || ip.isEmpty() || "127.0.0.1".equals(ip) || "localhost".equals(ip)) {
            return "本地";
        }
        if (ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.")) {
            return "局域网";
        }
        // 实际项目中可以集成 IP2Region 或其他 IP 地址库
        return "未知";
    }

}
