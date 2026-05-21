package com.fastproject.page.service;

import com.fastproject.page.vo.pagecomponent.PageComponentVo;
import com.fastproject.page.vo.pageconfig.*;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface PageConfigService {

    Long save(PageConfigCreate create);

    void update(PageConfigUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    PageConfigVo findById(Long id);

    PageVo<List<PageConfigVo>> findPage(PageConfigQuery query);

    List<PageConfigVo> findAll();

    List<PageConfigVo> selectAll();

    List<PageConfigListVo> findAllList(Long applicationId);

    /**
     * 发布页面
     * @param update 更新参数
     */
    void publish(PageConfigUpdate update);
}
