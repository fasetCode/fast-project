package com.fastproject.module.chat.controller;

import com.fastproject.module.chat.domain.Config;
import com.fastproject.module.chat.dto.InitSetupRequest;
import com.fastproject.module.chat.dto.WsTokenRequest;
import com.fastproject.module.chat.service.ConfigService;
import com.fastproject.module.chat.service.UserService;
import com.fastproject.utils.utils.HttpResult;
import com.fastproject.utils.utils.HttpUtils;
import com.fastproject.utils.utils.JsonUtils;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chat/init")
@RequiredArgsConstructor
@Slf4j
public class InitController {

    private final UserService userService;
    private final ConfigService configService;

    @GetMapping("/check")
    public ResultVo<Map<String, Boolean>> check() {
        Map<String, Boolean> result = new HashMap<>();
        result.put("hasUser", userService.count() > 0);
        result.put("hasConfig", configService.count() > 0);
        return ResultVo.success(result);
    }

    @PostMapping("/ws-token")
    public ResultVo<String> getWsToken(@RequestBody WsTokenRequest request) {
        String userId = request.getUserId();
        String groupId = request.getGroupId();
        String token = request.getToken();
        Config config = configService.getConfig();


        if (config == null) {
            return ResultVo.fail("配置不存在");
        }

        String tokenCallback = config.getTokenCallback();
        if (tokenCallback == null || tokenCallback.isEmpty()) {
            return ResultVo.fail("tokenCallback 未配置");
        }

        try {
            Map<String, String> params = new HashMap<>();
            params.put("userId", userId);
            params.put("groupId", groupId);
            params.put("token", token);
            HttpResult httpResult = HttpUtils.postJson(tokenCallback, JsonUtils.toJson(params));


            if (httpResult.getCode()!=200) {
                return ResultVo.fail("获取 ws token 失败: " + httpResult.getBody());
            }
            Map map = JsonUtils.fromJson(httpResult.getBody(), Map.class);
            return ResultVo.success(map.get("data").toString());
        } catch (Exception e) {
            log.error("获取 ws token 失败: {}", e.getMessage());
            return ResultVo.fail("获取 ws token 失败: " + e.getMessage());
        }
    }

    @PostMapping("/setup")
    @Transactional(rollbackFor = Exception.class)
    public ResultVo<Object> setup(@RequestBody InitSetupRequest request) {
        if (request.getUser() != null) {
            userService.save(request.getUser());
        }
        if (request.getConfig() != null) {
            configService.save(request.getConfig());
        }
        return ResultVo.success();
    }

    // 下面是需要认证的接口



}
