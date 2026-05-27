package com.fastproject.content.vo.revision;

import lombok.Data;

@Data
public class ContentRevisionCreate {
    private Long contentId;
    private Integer version;
    private Long editorId;
    private String reason;
    private String format;
    private String content;
    private String contentHtml;
    private Integer wordCount;
}
