package com.fastproject.content.vo.tag;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContentTagUpdate {
    @NotNull(message = "ID不能为空")
    private Long id;
    private String name;
    private String color;
    private String icon;
    private String image;
    private Integer displayType;
    private Integer status;
}
