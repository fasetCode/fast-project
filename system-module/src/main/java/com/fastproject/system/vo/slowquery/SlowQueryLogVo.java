package com.fastproject.system.vo.slowquery;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SlowQueryLogVo {
    private Long id;
    private String sqlContent;
    private String sqlMd5;
    private Long executionTime;
    private String severity;
    private String module;
    private Integer triggerCount;
    private String status;
    private String remark;
    private LocalDateTime createTime;
}
