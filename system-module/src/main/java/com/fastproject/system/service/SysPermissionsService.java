package com.fastproject.system.service;

import com.fastproject.system.vo.permissions.SysPermissionsUpdate;
import com.fastproject.system.vo.permissions.SysPermissionsCreate;
import com.fastproject.system.vo.permissions.SysPermissionsQuery;
import com.fastproject.system.vo.permissions.SysPermissionsVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 权限 Service 接口
 */
public interface SysPermissionsService {

    /**
     * 添加
     */
    Long save(SysPermissionsCreate create);

    /**
     * 修改
     */
    void update(SysPermissionsUpdate sysPermissionsUpdate);

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
    SysPermissionsVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysPermissionsVo>> findPage(SysPermissionsQuery query);

    /**
     * 查询所有权限(树形结构)
     */
    List<SysPermissionsVo> findTree();

    /**
     * 根据用户ID获取 按钮数据权限
     */
    List<String> findButtonPermissionsByUserId(Long userId);

    /**
     * 全部
     */
    List<String> findButtonPermissionsByALL();

    /**
     * 获取 用户的 所有的授权码
     */
    List<String> findAllPermissionsByUserId(Long userId);

    /**
     * 根据用户ID查询权限(树形结构)
     */
    List<SysPermissionsVo> findTreeByUserId(Long userId);


    /**
     * 全部
     */
    List<String> findAllPermissionsByAll();
}
