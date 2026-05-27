package com.fastproject.content.service;

import com.fastproject.content.vo.commentlike.ContentCommentLikeCreate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeQuery;
import com.fastproject.content.vo.commentlike.ContentCommentLikeUpdate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentCommentLikeService {
    Long save(ContentCommentLikeCreate create);

    void update(ContentCommentLikeUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentCommentLikeVo findById(Long id);

    PageVo<List<ContentCommentLikeVo>> findPage(ContentCommentLikeQuery query);

    List<ContentCommentLikeVo> findAll();

    List<ContentCommentLikeVo> selectAll();
}
