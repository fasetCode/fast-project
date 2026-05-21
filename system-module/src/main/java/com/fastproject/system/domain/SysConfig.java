package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 系统配置
 */
@Table(name = "sys_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysConfig extends BaseEntity {

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置类型
     */
    private String type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
