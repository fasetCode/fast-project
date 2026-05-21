package com.fastproject.page.service;

import com.fastproject.page.vo.pagewebconfig.PageWebConfigUpdate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigCreate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigQuery;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface PageWebConfigService {

    Long save(PageWebConfigCreate create);

    void update(PageWebConfigUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    PageWebConfigVo findById(Long id);

    PageVo<List<PageWebConfigVo>> findPage(PageWebConfigQuery query);

    List<PageWebConfigVo> findAll();

    List<PageWebConfigVo> selectAll();

    PageWebConfigVo findByConfigAndCode(String path, String appCode);
}
