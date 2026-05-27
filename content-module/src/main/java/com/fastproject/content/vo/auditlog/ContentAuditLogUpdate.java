package com.fastproject.content.vo.auditlog;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentAuditLogUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Integer auditStatus;
    private String reason;
    private Long auditBy;
    private LocalDateTime auditTime;
}
