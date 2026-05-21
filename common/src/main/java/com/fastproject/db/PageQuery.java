package com.fastproject.db;

import lombok.Data;
@Data
//@RegisterReflectionForBinding
public class PageQuery {
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页大小
     */
    private int pageSize;
}
