package com.fastproject.content.service;

import com.fastproject.content.vo.tag.ContentTagCreate;
import com.fastproject.content.vo.tag.ContentTagQuery;
import com.fastproject.content.vo.tag.ContentTagUpdate;
import com.fastproject.content.vo.tag.ContentTagVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentTagService {
    Long save(ContentTagCreate create);

    void update(ContentTagUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentTagVo findById(Long id);

    PageVo<List<ContentTagVo>> findPage(ContentTagQuery query);

    List<ContentTagVo> findAll();

    List<ContentTagVo> selectAll();
}
