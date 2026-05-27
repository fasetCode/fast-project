package com.fastproject.content.service;

import com.fastproject.content.vo.auditlog.ContentAuditLogCreate;
import com.fastproject.content.vo.auditlog.ContentAuditLogQuery;
import com.fastproject.content.vo.auditlog.ContentAuditLogUpdate;
import com.fastproject.content.vo.auditlog.ContentAuditLogVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentAuditLogService {
    Long save(ContentAuditLogCreate create);

    void update(ContentAuditLogUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentAuditLogVo findById(Long id);

    PageVo<List<ContentAuditLogVo>> findPage(ContentAuditLogQuery query);

    List<ContentAuditLogVo> findAll();

    List<ContentAuditLogVo> selectAll();
}
