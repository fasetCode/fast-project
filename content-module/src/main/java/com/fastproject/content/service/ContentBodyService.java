package com.fastproject.content.service;

import com.fastproject.content.vo.body.ContentBodyCreate;
import com.fastproject.content.vo.body.ContentBodyQuery;
import com.fastproject.content.vo.body.ContentBodyUpdate;
import com.fastproject.content.vo.body.ContentBodyVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentBodyService {
    Long save(ContentBodyCreate create);

    void update(ContentBodyUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentBodyVo findById(Long id);

    PageVo<List<ContentBodyVo>> findPage(ContentBodyQuery query);

    List<ContentBodyVo> findAll();

    List<ContentBodyVo> selectAll();
}
