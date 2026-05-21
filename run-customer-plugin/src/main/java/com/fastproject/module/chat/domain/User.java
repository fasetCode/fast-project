package com.fastproject.module.chat.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_user")
public class User {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 白名单 IP
     */
    private String whitelistIp;

    /**
     * 是否开启白名单
     */
    private Boolean whitelistEnabled;


}
