package com.fastproject.system.service;

import com.fastproject.system.vo.department.SysDepartmentUpdate;
import com.fastproject.system.vo.department.SysDepartmentCreate;
import com.fastproject.system.vo.department.SysDepartmentQuery;
import com.fastproject.system.vo.department.SysDepartmentVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 部门 Service 接口
 */
public interface SysDepartmentService {

    /**
     * 添加
     */
    Long save(SysDepartmentCreate create);

    /**
     * 修改
     */
    void update(SysDepartmentUpdate sysDepartmentUpdate);

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
    SysDepartmentVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysDepartmentVo>> findPage(SysDepartmentQuery query);

    /**
     * 查询所有部门(树形结构)
     */
    List<SysDepartmentVo> findTree();

    /**
     * 查询所有正常状态的部门(树形结构)，用于选择框
     */
    List<SysDepartmentVo> selectAll();
}
