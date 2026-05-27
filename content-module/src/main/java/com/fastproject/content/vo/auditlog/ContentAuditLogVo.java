package com.fastproject.content.vo.auditlog;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentAuditLogVo {
    private Long id;
    private Long contentId;
    private Integer auditStatus;
    private String reason;
    private Long auditBy;
    private LocalDateTime auditTime;
}
