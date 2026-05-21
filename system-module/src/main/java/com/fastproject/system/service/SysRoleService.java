package com.fastproject.system.service;

import com.fastproject.system.vo.role.SysRoleUpdate;
import com.fastproject.system.vo.role.SysRoleCreate;
import com.fastproject.system.vo.role.SysRoleQuery;
import com.fastproject.system.vo.role.SysRoleVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.SelectVo;

import java.util.List;

/**
 * 角色 Service 接口
 */
public interface SysRoleService {

    /**
     * 查询列表(用于选择框)
     */
    List<SelectVo> selectAll();

    /**
     * 添加
     */
    Long save(SysRoleCreate create);

    /**
     * 修改
     */
    void update(SysRoleUpdate sysRoleUpdate);

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
    SysRoleVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysRoleVo>> findPage(SysRoleQuery query);

}
