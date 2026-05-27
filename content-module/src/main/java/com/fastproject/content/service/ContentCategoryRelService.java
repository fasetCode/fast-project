package com.fastproject.content.service;

import com.fastproject.content.vo.categoryrel.ContentCategoryRelCreate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelQuery;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelUpdate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentCategoryRelService {
    Long save(ContentCategoryRelCreate create);

    void update(ContentCategoryRelUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentCategoryRelVo findById(Long id);

    PageVo<List<ContentCategoryRelVo>> findPage(ContentCategoryRelQuery query);

    List<ContentCategoryRelVo> findAll();

    List<ContentCategoryRelVo> selectAll();
}
