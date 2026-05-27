package com.fastproject.content.service;

import com.fastproject.content.vo.info.ContentInfoCreate;
import com.fastproject.content.vo.info.ContentInfoQuery;
import com.fastproject.content.vo.info.ContentInfoUpdate;
import com.fastproject.content.vo.info.ContentInfoVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentInfoService {
    Long save(ContentInfoCreate create);

    void update(ContentInfoUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentInfoVo findById(Long id);

    PageVo<List<ContentInfoVo>> findPage(ContentInfoQuery query);

    List<ContentInfoVo> findAll();

    List<ContentInfoVo> selectAll();
}
