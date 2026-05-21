package com.fastproject.module.chat.dto;

import com.fastproject.module.chat.domain.Config;
import com.fastproject.module.chat.domain.User;
import lombok.Data;

@Data
public class InitSetupRequest {
    private User user;
    private Config config;
}
