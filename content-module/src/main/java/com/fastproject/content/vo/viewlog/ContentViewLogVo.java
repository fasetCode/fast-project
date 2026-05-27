package com.fastproject.content.vo.viewlog;

import lombok.Data;

@Data
public class ContentViewLogVo {
    private Long id;
    private Long contentId;
    private Long userId;
    private String ip;
    private String userAgent;
    private String referer;
}
