package com.fastproject.content.vo.body;

import lombok.Data;

@Data
public class ContentBodyVo {
    private Long id;
    private Long contentId;
    private String format;
    private String content;
    private String contentHtml;
    private Integer wordCount;
    private Integer readingTime;
}
