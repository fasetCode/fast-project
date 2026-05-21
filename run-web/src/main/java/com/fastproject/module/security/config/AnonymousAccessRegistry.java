package com.fastproject.module.security.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AnonymousAccessRegistry {

    private final List<String> anonymousUrls = new ArrayList<>();

    public AnonymousAccessRegistry() {

        // 默认公共接口
        anonymousUrls.add("/common/page/config/getByPath");
        anonymousUrls.add("/file/getUrl/*");
    }

    public List<String> getAnonymousUrls() {
        return Collections.unmodifiableList(anonymousUrls);
    }
}
