package com.fastproject.content.vo.like;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentLikeUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Long userId;
}
