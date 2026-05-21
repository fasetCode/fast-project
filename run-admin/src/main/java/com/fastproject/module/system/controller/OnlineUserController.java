package com.fastproject.module.system.controller;

import com.fastproject.jedis.JedisTemplate;
import com.fastproject.props.SecurityProps;
import com.fastproject.utils.utils.JsonUtils;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import com.fastproject.vo.SecurityUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 在线用户管理 Controller
 */
@Slf4j
@RestController
@RequestMapping("/sys/online/users")
public class OnlineUserController {

    private final SecurityProps securityProps;
    private final JedisTemplate jedisTemplate;

    public OnlineUserController(SecurityProps securityProps, JedisTemplate jedisTemplate) {
        this.securityProps = securityProps;
        this.jedisTemplate = jedisTemplate;
    }

    /**
     * 在线用户分页查询
     *
     * @param page      当前页码（从0开始）
     * @param pageSize  每页条数
     * @param username  用户名（模糊查询）
     * @param nickname  昵称（模糊查询）
     * @return 在线用户分页列表
     */
    @GetMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:monitor:online:user')")
    public ResultVo<PageVo<List<OnlineUserVo>>> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname) {

        // 从 Redis 获取所有在线用户（根据 token key 模式查找）
        String tokenKeyPattern = securityProps.getCacheKey() + "*";
        Set<String> allKeys = jedisTemplate.keys(tokenKeyPattern);

        if (allKeys == null || allKeys.isEmpty()) {
            return ResultVo.success(PageVo.of(0L, new ArrayList<>()));
        }

        // 过滤出纯粹的 token key（不包含 userId: 的格式），避免重复
        List<String> tokenKeys = allKeys.stream()
                .filter(key -> {
                    String suffix = key.substring(securityProps.getCacheKey().length());
                    // 纯粹的 token 是 64 位 UUID，不包含冒号
                    return !suffix.contains(":");
                })
                .collect(Collectors.toList());

        // 获取所有在线用户信息
        List<OnlineUserVo> onlineUsers = new ArrayList<>();
        for (String tokenKey : tokenKeys) {
            String json = jedisTemplate.get(tokenKey);
            if (json != null) {
                try {
                    SecurityUserVo securityVo = JsonUtils.fromJson(json, SecurityUserVo.class);
                    if (securityVo != null && securityVo.getUser() != null) {
                        // 构建在线用户 VO
                        OnlineUserVo onlineUser = new OnlineUserVo();
                        onlineUser.setToken(tokenKey.substring(securityProps.getCacheKey().length()));
                        onlineUser.setUserId(securityVo.getUserId());
                        onlineUser.setUsername(securityVo.getUser().getUsername());
                        onlineUser.setNickname(securityVo.getUser().getNickname());
                        onlineUser.setEmail(securityVo.getUser().getEmail());
                        onlineUser.setPhone(securityVo.getUser().getPhone());
                        onlineUser.setIp(securityVo.getIp());
                        onlineUser.setAddress(securityVo.getAddress());
                        onlineUser.setBrowser(securityVo.getBrowser());
                        onlineUser.setDevice(securityVo.getDevice());
                        onlineUser.setLoginTime(securityVo.getLoginTime());

                        // 计算剩余有效时间（秒）
                        Long ttl = jedisTemplate.ttl(tokenKey);
                        onlineUser.setExpireTime(ttl != null ? ttl : 0L);

                        onlineUsers.add(onlineUser);
                    }
                } catch (Exception e) {
                    log.warn("解析在线用户数据失败: {}", tokenKey, e);
                }
            }
        }

        // 根据用户名和昵称过滤
        if (username != null && !username.isEmpty()) {
            onlineUsers = onlineUsers.stream()
                    .filter(u -> u.getUsername() != null && u.getUsername().contains(username))
                    .collect(Collectors.toList());
        }
        if (nickname != null && !nickname.isEmpty()) {
            onlineUsers = onlineUsers.stream()
                    .filter(u -> u.getNickname() != null && u.getNickname().contains(nickname))
                    .collect(Collectors.toList());
        }

        // 按登录时间倒序排序
        onlineUsers.sort((a, b) -> Long.compare(b.getLoginTime(), a.getLoginTime()));

        // 分页处理
        int total = onlineUsers.size();
        int start = page * pageSize;
        int end = Math.min(start + pageSize, total);

        List<OnlineUserVo> pageData = start < total ? onlineUsers.subList(start, end) : new ArrayList<>();

        return ResultVo.success(PageVo.of((long) total, pageData));
    }

    /**
     * 强制踢出在线用户（强制下线）
     *
     * @param token 用户 token
     * @return 操作结果
     */
    @DeleteMapping("/kickout/{token}")
    @PreAuthorize("@ps.hasPermission('admin:monitor:online:kickout')")
    public ResultVo<Object> kickout(@PathVariable String token) {
        if (token == null || token.isEmpty()) {
            return ResultVo.fail("token不能为空");
        }

        String redisTokenKey = securityProps.getCacheKey() + token;
        String json = jedisTemplate.get(redisTokenKey);

        if (json != null) {
            try {
                SecurityUserVo securityVo = JsonUtils.fromJson(json, SecurityUserVo.class);
                if (securityVo != null) {
                    Long userId = securityVo.getUserId();
                    String redisUserKey = securityProps.getCacheKey() + userId + ":" + token;

                    // 删除 Redis 中的两份 key
                    jedisTemplate.del(redisTokenKey);
                    jedisTemplate.del(redisUserKey);

                    log.info("强制踢出用户: {}, userId: {}", securityVo.getUser().getUsername(), userId);
                    return ResultVo.success();
                }
            } catch (Exception e) {
                            log.error("踢出用户失败", e);
                            return ResultVo.fail("操作失败");            }
        }

        return ResultVo.fail("用户已离线或token无效");
    }

    /**
     * 批量踢出在线用户
     *
     * @param tokens token 列表
     * @return 操作结果
     */
    @PostMapping("/kickout/batch")
    @PreAuthorize("@ps.hasPermission('admin:monitor:online:kickout')")
    public ResultVo<Object> batchKickout(@RequestBody List<String> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            return ResultVo.fail("token列表不能为空");
        }

        int successCount = 0;
        for (String token : tokens) {
            String redisTokenKey = securityProps.getCacheKey() + token;
            String json = jedisTemplate.get(redisTokenKey);

            if (json != null) {
                try {
                    SecurityUserVo securityVo = JsonUtils.fromJson(json, SecurityUserVo.class);
                    if (securityVo != null) {
                        Long userId = securityVo.getUserId();
                        String redisUserKey = securityProps.getCacheKey() + userId + ":" + token;

                        jedisTemplate.del(redisTokenKey);
                        jedisTemplate.del(redisUserKey);
                        successCount++;
                    }
                } catch (Exception e) {
                    log.warn("踢出用户失败: {}", token, e);
                }
            }
        }

        log.info("批量踢出用户成功，数量: {}", successCount);
        return ResultVo.success();
    }

    /**
     * 获取在线用户统计信息
     *
     * @return 统计信息
     */
    @GetMapping("/statistics")
    @PreAuthorize("@ps.hasPermission('admin:monitor:online:user')")
    public ResultVo<OnlineStatisticsVo> statistics() {
        String tokenKeyPattern = securityProps.getCacheKey() + "*";
        Set<String> allKeys = jedisTemplate.keys(tokenKeyPattern);

        long onlineCount = 0;
        Set<Long> uniqueUsers = new HashSet<>();

        if (allKeys != null) {
            List<String> tokenKeys = allKeys.stream()
                    .filter(key -> !key.substring(securityProps.getCacheKey().length()).contains(":"))
                    .collect(Collectors.toList());

            onlineCount = tokenKeys.size();

            for (String tokenKey : tokenKeys) {
                String json = jedisTemplate.get(tokenKey);
                if (json != null) {
                    try {
                        SecurityUserVo securityVo = JsonUtils.fromJson(json, SecurityUserVo.class);
                        if (securityVo != null) {
                            uniqueUsers.add(securityVo.getUserId());
                        }
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        OnlineStatisticsVo vo = new OnlineStatisticsVo();
        vo.setOnlineCount(onlineCount);
        vo.setUniqueUserCount((long) uniqueUsers.size());

        return ResultVo.success(vo);
    }

    /**
     * 在线用户 VO
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class OnlineUserVo {
        /** token */
        private String token;
        /** 用户ID */
        private Long userId;
        /** 用户名 */
        private String username;
        /** 昵称 */
        private String nickname;
        /** 邮箱 */
        private String email;
        /** 电话 */
        private String phone;
        /** 登录IP */
        private String ip;
        /** 登录地点 */
        private String address;
        /** 浏览器 */
        private String browser;
        /** 设备 */
        private String device;
        /** 登录时间戳 */
        private Long loginTime;
        /** 剩余有效时间（秒） */
        private Long expireTime;
    }

    /**
     * 在线用户统计 VO
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class OnlineStatisticsVo {
        /** 在线会话数 */
        private Long onlineCount;
        /** 在线用户数（去重） */
        private Long uniqueUserCount;
    }
}
