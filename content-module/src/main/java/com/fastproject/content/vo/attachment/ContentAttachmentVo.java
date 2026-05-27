package com.fastproject.content.vo.attachment;

import lombok.Data;

@Data
public class ContentAttachmentVo {
    private Long id;
    private Long contentId;
    private Long fileId;
    private String url;
    private String name;
    private String mimeType;
    private Long size;
    private Integer sort;
}
