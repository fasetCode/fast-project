package com.fastproject.content.vo.body;

import lombok.Data;

@Data
public class ContentBodyCreate {
    private Long contentId;
    private String format;
    private String content;
    private String contentHtml;
    private Integer wordCount;
    private Integer readingTime;
}
