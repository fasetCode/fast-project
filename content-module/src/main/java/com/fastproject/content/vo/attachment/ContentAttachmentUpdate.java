package com.fastproject.content.vo.attachment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentAttachmentUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Long fileId;
    private String url;
    private String name;
    private String mimeType;
    private Long size;
    private Integer sort;
}
