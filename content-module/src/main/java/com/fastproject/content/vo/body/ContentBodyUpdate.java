package com.fastproject.content.vo.body;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentBodyUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private String format;
    private String content;
    private String contentHtml;
    private Integer wordCount;
    private Integer readingTime;
}
