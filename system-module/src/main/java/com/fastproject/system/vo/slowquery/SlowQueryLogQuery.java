package com.fastproject.system.vo.slowquery;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SlowQueryLogQuery extends PageQuery {
    private String sqlContent;
    private String sqlMd5;
    private String severity;
    private String module;
    private String status;
}
