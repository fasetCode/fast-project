package com.fastproject.content.service;

import com.fastproject.content.vo.revision.ContentRevisionCreate;
import com.fastproject.content.vo.revision.ContentRevisionQuery;
import com.fastproject.content.vo.revision.ContentRevisionUpdate;
import com.fastproject.content.vo.revision.ContentRevisionVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentRevisionService {
    Long save(ContentRevisionCreate create);

    void update(ContentRevisionUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentRevisionVo findById(Long id);

    ContentRevisionVo findLatestByContentId(Long contentId);

    PageVo<List<ContentRevisionVo>> findPage(ContentRevisionQuery query);

    List<ContentRevisionVo> findAll();

    List<ContentRevisionVo> selectAll();
}
