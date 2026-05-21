package com.fastproject.logs.vo;

import com.fastproject.db.PageQuery;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperationLogQuery extends PageQuery {

    /**
     * 日志描述
     */
    private String description;

    /**
     * 日志类型
     */
    private LogType type;

    /**
     * 操作方法
     */
    private LogAction action;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}
