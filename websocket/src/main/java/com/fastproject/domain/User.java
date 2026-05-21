package com.fastproject.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ws_user")
public class User {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
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
}
