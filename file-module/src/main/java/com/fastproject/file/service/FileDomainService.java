package com.fastproject.file.service;

import com.fastproject.file.vo.domain.FileDomainCreate;
import com.fastproject.file.vo.domain.FileDomainQuery;
import com.fastproject.file.vo.domain.FileDomainUpdate;
import com.fastproject.file.vo.domain.FileDomainVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 文件域名 Service 接口
 */
public interface FileDomainService {

    /**
     * 添加
     */
    Long save(FileDomainCreate create);

    /**
     * 修改
     */
    void update(FileDomainUpdate update);

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
    FileDomainVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<FileDomainVo>> findPage(FileDomainQuery query);

    /**
     * 根据域名查询
     */
    FileDomainVo findByDomain(String domain);

    /**
     * 根据配置ID查询域名列表
     */
    List<FileDomainVo> findByConfigId(Long configId);

    /**
     * 获取所有启用的域名
     */
    List<FileDomainVo> findAllEnabled();


    /**
     * 获取域名列表
     */
    List<FileDomainVo> findAllByConfigId(Long configId);
}
