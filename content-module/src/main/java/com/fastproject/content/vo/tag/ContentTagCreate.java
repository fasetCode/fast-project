package com.fastproject.content.vo.tag;

import lombok.Data;

@Data
public class ContentTagCreate {
    private String name;
    private String color;
    private String icon;
    private String image;
    private Integer displayType;
    private Integer status;
}
