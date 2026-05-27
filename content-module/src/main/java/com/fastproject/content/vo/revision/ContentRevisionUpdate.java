package com.fastproject.content.vo.revision;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentRevisionUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Integer version;
    private Long editorId;
    private String reason;
    private String format;
    private String content;
    private String contentHtml;
    private Integer wordCount;
}
