package com.fastproject.content.service;

import com.fastproject.content.vo.attachment.ContentAttachmentCreate;
import com.fastproject.content.vo.attachment.ContentAttachmentQuery;
import com.fastproject.content.vo.attachment.ContentAttachmentUpdate;
import com.fastproject.content.vo.attachment.ContentAttachmentVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentAttachmentService {
    Long save(ContentAttachmentCreate create);

    void update(ContentAttachmentUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentAttachmentVo findById(Long id);

    PageVo<List<ContentAttachmentVo>> findPage(ContentAttachmentQuery query);

    List<ContentAttachmentVo> findAll();

    List<ContentAttachmentVo> selectAll();
}
