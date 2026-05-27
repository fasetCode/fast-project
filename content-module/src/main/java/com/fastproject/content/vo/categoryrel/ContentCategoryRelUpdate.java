package com.fastproject.content.vo.categoryrel;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentCategoryRelUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Long categoryId;
}
