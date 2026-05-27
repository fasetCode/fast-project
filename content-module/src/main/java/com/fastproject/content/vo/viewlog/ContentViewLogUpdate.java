package com.fastproject.content.vo.viewlog;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentViewLogUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Long userId;
    private String ip;
    private String userAgent;
    private String referer;
}
