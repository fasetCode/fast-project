package com.fastproject.system.vo.users;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUsersPickerQuery extends PageQuery {
    public SysUsersPickerQuery() {
    }

    /**
     * 关键字（账号/昵称）
     */
    private String keyword;
}

