package com.fastproject.content.vo.viewlog;

import lombok.Data;

@Data
public class ContentViewLogCreate {
    private Long contentId;
    private Long userId;
    private String ip;
    private String userAgent;
    private String referer;
}
