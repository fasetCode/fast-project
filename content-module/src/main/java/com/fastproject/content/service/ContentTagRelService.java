package com.fastproject.content.service;

import com.fastproject.content.vo.tagrel.ContentTagRelCreate;
import com.fastproject.content.vo.tagrel.ContentTagRelQuery;
import com.fastproject.content.vo.tagrel.ContentTagRelUpdate;
import com.fastproject.content.vo.tagrel.ContentTagRelVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentTagRelService {
    Long save(ContentTagRelCreate create);

    void update(ContentTagRelUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentTagRelVo findById(Long id);

    PageVo<List<ContentTagRelVo>> findPage(ContentTagRelQuery query);

    List<ContentTagRelVo> findAll();

    List<ContentTagRelVo> selectAll();
}
