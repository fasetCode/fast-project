package com.fastproject.utils.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("admin", "超级管理员");

    private final String code;
    private final String message;

    RoleEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
