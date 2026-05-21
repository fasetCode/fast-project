package com.fastproject.message.send.vo;

import lombok.Data;

/**
 * 邮件发送配置 VO
 * 用于接收并映射 JSON 格式的邮件配置信息
 * 
 * JSON 配置示例：
 * {
 *   "host": "smtp.qq.com",
 *   "port": 465,
 *   "username": "example@qq.com",
 *   "password": "your_auth_code_here",
 *   "protocol": "smtps",
 *   "defaultEncoding": "UTF-8",
 *   "auth": true,
 *   "starttlsEnable": true,
 *   "from": "example@qq.com"
 * }
 */
@Data
public class MailConfigVo {

    /**
     * 邮件服务器地址 (例如：smtp.qq.com)
     */
    private String host;

    /**
     * 邮件服务器端口 (例如：465 或 587)
     */
    private Integer port;

    /**
     * 邮箱账号/用户名
     */
    private String username;

    /**
     * 邮箱授权码或密码
     */
    private String password;

    /**
     * 发送协议，默认为 smtp
     */
    private String protocol = "smtp";

    /**
     * 默认编码，默认为 UTF-8
     */
    private String defaultEncoding = "UTF-8";

    /**
     * 是否需要身份验证，默认为 true
     */
    private Boolean auth = true;

    /**
     * 是否启用 STARTTLS 安全连接，默认为 true
     */
    private Boolean starttlsEnable = true;

    /**
     * 发件人邮箱地址（如果不设置，通常默认使用 username）
     */
    private String from;
}
