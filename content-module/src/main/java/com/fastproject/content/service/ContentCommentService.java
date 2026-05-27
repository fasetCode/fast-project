package com.fastproject.content.service;

import com.fastproject.content.vo.comment.ContentCommentCreate;
import com.fastproject.content.vo.comment.ContentCommentQuery;
import com.fastproject.content.vo.comment.ContentCommentUpdate;
import com.fastproject.content.vo.comment.ContentCommentVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentCommentService {
    Long save(ContentCommentCreate create);

    void update(ContentCommentUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentCommentVo findById(Long id);

    PageVo<List<ContentCommentVo>> findPage(ContentCommentQuery query);

    List<ContentCommentVo> findAll();

    List<ContentCommentVo> selectAll();
}
