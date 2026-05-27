package com.fastproject.content.vo.tagrel;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentTagRelUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private Long contentId;
    private Long tagId;
}
