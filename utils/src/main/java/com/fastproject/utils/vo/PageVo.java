package com.fastproject.utils.vo;

import lombok.Data;

@Data
public class PageVo<T> {
    /**
     * 总数
     */
    private long total;

    /**
     * 数据
     */
    private T data;

    public static <T> PageVo<T> of(long total, T data) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setTotal(total);
        pageVo.setData(data);
        return pageVo;
    }
}
