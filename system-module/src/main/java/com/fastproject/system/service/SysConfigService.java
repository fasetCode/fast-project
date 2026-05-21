package com.fastproject.system.service;

import com.fastproject.system.vo.config.SysConfigUpdate;
import com.fastproject.system.vo.config.SysConfigCreate;
import com.fastproject.system.vo.config.SysConfigQuery;
import com.fastproject.system.vo.config.SysConfigVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 系统配置 Service 接口
 */
public interface SysConfigService {

    /**
     * 添加
     */
    Long save(SysConfigCreate create);

    /**
     * 修改
     */
    void update(SysConfigUpdate sysConfigUpdate);

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
    SysConfigVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysConfigVo>> findPage(SysConfigQuery query);

    /**
     * 根据配置键查询配置值
     */
    String getConfigValue(String configKey);
}
