package com.fastproject.system.service;

import com.fastproject.system.vo.dictdata.SysDictDataUpdate;
import com.fastproject.system.vo.dictdata.SysDictDataCreate;
import com.fastproject.system.vo.dictdata.SysDictDataQuery;
import com.fastproject.system.vo.dictdata.SysDictDataVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 字典数据 Service 接口
 */
public interface SysDictDataService {

    /**
     * 添加
     */
    Long save(SysDictDataCreate create);

    /**
     * 修改
     */
    void update(SysDictDataUpdate sysDictDataUpdate);

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
    SysDictDataVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysDictDataVo>> findPage(SysDictDataQuery query);

    /**
     * 根据字典类型ID查询字典数据
     */
    List<SysDictDataVo> findByDictTypeId(Long dictTypeId);
}
