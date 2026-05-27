package com.fastproject.content.vo.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentCommentUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Long parentId;
    private Long rootId;
    private Long userId;
    private String nickname;
    private String avatar;
    private String content;
    private Integer status;
    private Long likeCount;
    private String ip;
    private String userAgent;
    private Long replyToUserId;
    private Long replyToCommentId;
}
