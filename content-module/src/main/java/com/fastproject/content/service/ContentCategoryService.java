package com.fastproject.content.service;

import com.fastproject.content.vo.category.ContentCategoryCreate;
import com.fastproject.content.vo.category.ContentCategoryQuery;
import com.fastproject.content.vo.category.ContentCategoryUpdate;
import com.fastproject.content.vo.category.ContentCategoryVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentCategoryService {
    Long save(ContentCategoryCreate create);

    void update(ContentCategoryUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentCategoryVo findById(Long id);

    PageVo<List<ContentCategoryVo>> findPage(ContentCategoryQuery query);

    List<ContentCategoryVo> findAll();

    List<ContentCategoryVo> selectAll();

    List<ContentCategoryVo> findTree();
}
