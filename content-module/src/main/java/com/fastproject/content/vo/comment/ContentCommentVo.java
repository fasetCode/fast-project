package com.fastproject.content.vo.comment;

import lombok.Data;

@Data
public class ContentCommentVo {
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
