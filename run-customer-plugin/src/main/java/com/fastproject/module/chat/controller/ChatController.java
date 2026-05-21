package com.fastproject.module.chat.controller;

import com.fastproject.module.chat.domain.Config;
import com.fastproject.module.chat.service.ConfigService;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ConfigService configService;

    @GetMapping("/ws-domain")
    public ResultVo<String> getWsDomain() {
        Config config = configService.getConfig();
        return config != null ? ResultVo.success(config.getWsDomain()) : ResultVo.fail("配置不存在");
    }
}
