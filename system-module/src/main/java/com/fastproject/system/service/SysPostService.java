package com.fastproject.system.service;

import com.fastproject.system.vo.post.SysPostUpdate;
import com.fastproject.system.vo.post.SysPostCreate;
import com.fastproject.system.vo.post.SysPostQuery;
import com.fastproject.system.vo.post.SysPostVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 岗位 Service 接口
 */
public interface SysPostService {

    /**
     * 添加
     */
    Long save(SysPostCreate create);

    /**
     * 修改
     */
    void update(SysPostUpdate sysPostUpdate);

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
    SysPostVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysPostVo>> findPage(SysPostQuery query);

    /**
     * 查询所有岗位(列表)
     */
    List<SysPostVo> findAll();

    /**
     * 查询所有正常状态的岗位，用于选择框
     */
    List<SysPostVo> selectAll();
}
