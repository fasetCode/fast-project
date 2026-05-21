package com.fastproject.utils.vo;

import lombok.Data;

@Data
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResultVo() {
    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功
    public static <T> ResultVo<T> success() {
        return new ResultVo<T>(200, "success", null);
    }

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<T>(200, "success", data);
    }

    public static <T> ResultVo<T> success(String msg, T data) {
        return new ResultVo<T>(200, msg, data);
    }

    // 失败
    public static <T> ResultVo<T> fail() {
        return new ResultVo<T>(500, "fail", null);
    }

    public static <T> ResultVo<T> fail(String msg) {
        return new ResultVo<T>(500, msg, null);
    }

    public static <T> ResultVo<T> fail(Integer code, String msg) {
        return new ResultVo<T>(code, msg, null);
    }

}
