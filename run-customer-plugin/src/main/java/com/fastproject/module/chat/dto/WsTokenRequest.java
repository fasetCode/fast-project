package com.fastproject.module.chat.dto;

import lombok.Data;

@Data
public class WsTokenRequest {
    private String userId;
    private String groupId;
    private String token;
}
