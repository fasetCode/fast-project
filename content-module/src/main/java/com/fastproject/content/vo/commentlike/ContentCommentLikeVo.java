package com.fastproject.content.vo.commentlike;

import lombok.Data;

@Data
public class ContentCommentLikeVo {
    private Long id;
    private Long contentId;
    private Long commentId;
    private Long userId;
    private Integer action;
}
