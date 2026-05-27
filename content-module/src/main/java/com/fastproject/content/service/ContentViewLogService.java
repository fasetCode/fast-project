package com.fastproject.content.service;

import com.fastproject.content.vo.viewlog.ContentViewLogCreate;
import com.fastproject.content.vo.viewlog.ContentViewLogQuery;
import com.fastproject.content.vo.viewlog.ContentViewLogUpdate;
import com.fastproject.content.vo.viewlog.ContentViewLogVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentViewLogService {
    Long save(ContentViewLogCreate create);

    void update(ContentViewLogUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentViewLogVo findById(Long id);

    PageVo<List<ContentViewLogVo>> findPage(ContentViewLogQuery query);

    List<ContentViewLogVo> findAll();

    List<ContentViewLogVo> selectAll();
}
