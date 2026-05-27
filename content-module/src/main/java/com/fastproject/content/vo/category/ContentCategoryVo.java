package com.fastproject.content.vo.category;

import lombok.Data;

import java.util.List;

@Data
public class ContentCategoryVo {
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private Integer status;
    private String remark;
    private List<ContentCategoryVo> children;
}
