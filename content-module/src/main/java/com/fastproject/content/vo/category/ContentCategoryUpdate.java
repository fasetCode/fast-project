package com.fastproject.content.vo.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentCategoryUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private Integer status;
    private String remark;
}
