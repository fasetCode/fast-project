package com.fastproject.page.service;

import com.fastproject.page.vo.pageapplication.PageApplicationUpdate;
import com.fastproject.page.vo.pageapplication.PageApplicationCreate;
import com.fastproject.page.vo.pageapplication.PageApplicationQuery;
import com.fastproject.page.vo.pageapplication.PageApplicationVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface PageApplicationService {

    Long save(PageApplicationCreate create);

    void update(PageApplicationUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    PageApplicationVo findById(Long id);

    PageVo<List<PageApplicationVo>> findPage(PageApplicationQuery query);

    List<PageApplicationVo> findAll();

    List<PageApplicationVo> selectAll();
}
