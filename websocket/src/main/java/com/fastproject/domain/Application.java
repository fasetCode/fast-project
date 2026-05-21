package com.fastproject.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ws_application")
public class Application {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 授权回调
     */
    private String authCallback;

    /**
     * 发送消息回调
     */
    private String sendCallback;

    /**
     * 应用状态
     */
    private Integer status;

}
