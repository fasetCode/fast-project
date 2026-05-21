package com.fastproject.page.service;

import com.fastproject.page.vo.pagetype.PageTypeUpdate;
import com.fastproject.page.vo.pagetype.PageTypeCreate;
import com.fastproject.page.vo.pagetype.PageTypeQuery;
import com.fastproject.page.vo.pagetype.PageTypeVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface PageTypeService {

    Long save(PageTypeCreate create);

    void update(PageTypeUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    PageTypeVo findById(Long id);

    PageVo<List<PageTypeVo>> findPage(PageTypeQuery query);

    List<PageTypeVo> findAll();

    List<PageTypeVo> selectAll();
}
