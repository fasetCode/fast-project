package com.fastproject.module.chat.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_config")
public class Config {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息发送地址
     */
    private String messageSendUrl;

    /**
     * 消息发送密钥
     */
    private String messageSendKey;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 客服分组
     */
    private String wsGroup;

    /**
     * 客户分组
     */
    private String customerGroup;

    /**
     * 备注
     */
    private String remark;

    /**
     * ws链接域名
     */
    private String wsDomain;

    /**
     * 客服系统 授权回调
     */
    private String authCallback;

    /**
     * 消息回调
     */
    private String messageCallback;

    /**
     * 获取 token回调
     */
    private String tokenCallback;

    /**
     * 用户信息列表 接口
     */
    private String userInfoList;

    /**
     * 1 统一客服 2 多客服
     */
    private Integer type;

    /**
     * 获取客服列表接口
     */
    private String getCsList;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;
}
