package com.fastproject.content.vo.info;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContentInfoQuery extends PageQuery {
    private String title;
    private Long authorId;
    private Long auditBy;
    private List<Long> categoryIds;
    private List<Long> tagIds;
}
