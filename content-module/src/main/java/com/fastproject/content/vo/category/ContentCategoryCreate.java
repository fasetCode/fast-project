package com.fastproject.content.vo.category;

import lombok.Data;

@Data
public class ContentCategoryCreate {
    private String name;
    private Long parentId;
    private Integer sort;
    private Integer status;
    private String remark;
}
