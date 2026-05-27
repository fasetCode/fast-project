package com.fastproject.content.vo.categoryrel;

import lombok.Data;

@Data
public class ContentCategoryRelVo {
    private Long id;
    private Long contentId;
    private Long categoryId;
}
