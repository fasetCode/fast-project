package com.fastproject.ratelimit.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "fastproject.ratelimit")
public class RateLimitProps {

    private String appCode;

    private boolean enableIpBwFilter = true;

    private boolean enableUserBwFilter = true;
}
