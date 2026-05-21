package com.fastproject.utils.enums;

public enum StatusEnum {
    NORMAL(1, "正常"),
    DISABLED(2, "冻结");

    private final Integer code;
    private final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
