package com.fastproject.content.vo.report;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentReportUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Integer targetType;
    private Long targetId;
    private Long reportBy;
    private Integer reasonType;
    private String reason;
    private Integer status;
    private Long handleBy;
    private LocalDateTime handleTime;
    private String ip;
    private String userAgent;
}
