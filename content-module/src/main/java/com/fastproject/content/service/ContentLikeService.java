package com.fastproject.content.service;

import com.fastproject.content.vo.like.ContentLikeCreate;
import com.fastproject.content.vo.like.ContentLikeQuery;
import com.fastproject.content.vo.like.ContentLikeUpdate;
import com.fastproject.content.vo.like.ContentLikeVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentLikeService {
    Long save(ContentLikeCreate create);

    void update(ContentLikeUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentLikeVo findById(Long id);

    PageVo<List<ContentLikeVo>> findPage(ContentLikeQuery query);

    List<ContentLikeVo> findAll();

    List<ContentLikeVo> selectAll();
}
