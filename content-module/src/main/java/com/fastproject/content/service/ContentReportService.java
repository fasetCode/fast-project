package com.fastproject.content.service;

import com.fastproject.content.vo.report.ContentReportCreate;
import com.fastproject.content.vo.report.ContentReportQuery;
import com.fastproject.content.vo.report.ContentReportUpdate;
import com.fastproject.content.vo.report.ContentReportVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentReportService {
    Long save(ContentReportCreate create);

    void update(ContentReportUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentReportVo findById(Long id);

    PageVo<List<ContentReportVo>> findPage(ContentReportQuery query);

    List<ContentReportVo> findAll();

    List<ContentReportVo> selectAll();
}
