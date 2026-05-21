package com.fastproject.module.chat.domain;

import jakarta.persistence.*;
import lombok.Data;

/**
 * 客服列表
 */
@Data
@Entity
@Table(name = "customer_service_user")
public class CustomerUser {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

}
