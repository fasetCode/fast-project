package com.fastproject.content.vo.commentlike;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentCommentLikeUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Long commentId;
    private Long userId;
    private Integer action;
}
