package com.fastproject.page.service;

import com.fastproject.page.vo.pagecomponent.PageComponentUpdate;
import com.fastproject.page.vo.pagecomponent.PageComponentCreate;
import com.fastproject.page.vo.pagecomponent.PageComponentQuery;
import com.fastproject.page.vo.pagecomponent.PageComponentVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface PageComponentService {

    Long save(PageComponentCreate create);

    void update(PageComponentUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    PageComponentVo findById(Long id);

    PageVo<List<PageComponentVo>> findPage(PageComponentQuery query);

    List<PageComponentVo> findAll();

    List<PageComponentVo> selectAll();

    List<PageComponentVo> findByTypeId(Long typeId);
}
