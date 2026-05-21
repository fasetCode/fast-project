package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import com.fastproject.system.enums.SlowQuerySeverity;
import com.fastproject.system.enums.SlowQueryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 慢查询日志
 */
@Table(name = "sys_slow_query_log")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_slow_query_log set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SlowQueryLog extends BaseEntity {

    /**
     * SQL内容
     */
    @Column(columnDefinition = "TEXT")
    private String sqlContent;

    /**
     * SQL MD5摘要
     */
    private String sqlMd5;

    /**
     * 执行耗时 (毫秒)
     */
    private Long executionTime;

    /**
     * 严重程度
     */
    @Enumerated(EnumType.STRING)
    private SlowQuerySeverity severity;

    /**
     * 业务模块
     */
    private String module;

    /**
     * 触发次数
     */
    private Integer triggerCount;

    /**
     * 处理状态
     */
    @Enumerated(EnumType.STRING)
    private SlowQueryStatus status;

    /**
     * 备注
     */
    private String remark;
}
