package com.fastproject.ratelimit.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.ratelimit.enums.BlackWhiteListType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 用户黑白名单配置
 */
@Table(name = "rate_limit_user_black_white")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update rate_limit_user_black_white set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class UserBlackWhiteList extends BaseEntity {

    /**
     * 应用代码 (可用于区分不同应用/服务的规则)
     */
    @Column(name = "app_code")
    private String appCode;

    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 名单类型: BLACK-黑名单, WHITE-白名单
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "list_type", nullable = false)
    private BlackWhiteListType listType;

    /**
     * 限制提示信息 (当触发黑名单时的提示语)
     */
    @Column(name = "limit_msg")
    private String limitMsg;

    /**
     * 是否启用 (1-启用, 0-禁用)
     */
    @Column(name = "enabled")
    private Boolean enabled;

    /**
     * 备注说明
     */
    @Column(name = "remark")
    private String remark;

}
