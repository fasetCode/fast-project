package com.fastproject.content.vo.info;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentInfoUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private String title;
    private String summary;
    private String cover;
    private Long categoryId;
    private String tags;
    private Long authorId;
    private String authorName;
    private String source;
    private String sourceUrl;
    private Boolean topFlag;
    private Boolean recommendFlag;
    private Boolean allowComment;
    private Integer status;
    private Integer publishStatus;
    private Integer auditStatus;
    private LocalDateTime auditTime;
    private Long auditBy;
    private LocalDateTime publishTime;
    private LocalDateTime lastCommentTime;
    private Long viewCount;
    private Long likeCount;
    private Long favoriteCount;
    private Long commentCount;
}
