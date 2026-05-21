package com.fastproject.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "security")
@Component
@Data
public class SecurityProps {

    // 过期时间 分钟
    private int expire;

    // 缓存 key
    private String cacheKey;

    // 前端的token key
    private String tokenKey;

    // 运行多少设备 0 表示不限制
    private int device;

}
