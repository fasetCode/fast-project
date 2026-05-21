package com.fastproject.system.service;

import com.fastproject.system.vo.dictdata.Dict;
import com.fastproject.system.vo.dicttype.SysDictTypeUpdate;
import com.fastproject.system.vo.dicttype.SysDictTypeCreate;
import com.fastproject.system.vo.dicttype.SysDictTypeQuery;
import com.fastproject.system.vo.dicttype.SysDictTypeVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 字典类型 Service 接口
 */
public interface SysDictTypeService {

    /**
     * 添加
     */
    Long save(SysDictTypeCreate create);

    /**
     * 修改
     */
    void update(SysDictTypeUpdate sysDictTypeUpdate);

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
    SysDictTypeVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysDictTypeVo>> findPage(SysDictTypeQuery query);

    /**
     * 查询所有字典类型
     */
    List<SysDictTypeVo> findAll();

    /**
     * 查询所有正常状态的字典类型，用于选择框
     */
    List<SysDictTypeVo> selectAll();

    /**
     * 获取所有字典数据（包含类型和对应数据）
     */
    List<Dict> findAllDict();
}
