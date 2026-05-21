package com.fastproject.module.security.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class AnonymousAccessRegistry {

    private final List<String> anonymousUrls = new ArrayList<>();

    public AnonymousAccessRegistry() {

        // 默认公共接口
        anonymousUrls.add("/auth/login");
        anonymousUrls.add("/auth/captcha");

        anonymousUrls.add("/ws/auth");
        anonymousUrls.add("/chat/auth");
        anonymousUrls.add("/ws/getToken");
        anonymousUrls.add("/file/getUrl/**");
//        anonymousUrls.add("/auth/refreshToken");
//        anonymousUrls.add("/**");
    }

    public List<String> getAnonymousUrls() {
        return Collections.unmodifiableList(anonymousUrls);
    }
}
