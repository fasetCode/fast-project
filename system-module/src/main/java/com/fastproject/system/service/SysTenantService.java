package com.fastproject.system.service;

import com.fastproject.system.vo.tenant.SysTenantCreate;
import com.fastproject.system.vo.tenant.SysTenantQuery;
import com.fastproject.system.vo.tenant.SysTenantUpdate;
import com.fastproject.system.vo.tenant.SysTenantVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 租户 Service 接口
 */
public interface SysTenantService {

    /**
     * 添加
     */
    Long save(SysTenantCreate create);

    /**
     * 修改
     */
    void update(SysTenantUpdate sysTenantUpdate);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 批量删除
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询
     */
    SysTenantVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysTenantVo>> findPage(SysTenantQuery query);
}
