package com.fastproject.mall.service;

import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateCreate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateQuery;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateUpdate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 运费模板 Service 接口
 */
public interface MallFreightTemplateService {

    Long save(MallFreightTemplateCreate create);

    void update(MallFreightTemplateUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    MallFreightTemplateVo findById(Long id);

    PageVo<List<MallFreightTemplateVo>> findPage(MallFreightTemplateQuery query);

    List<MallFreightTemplateVo> findAll();

    List<MallFreightTemplateVo> selectAll();
}
