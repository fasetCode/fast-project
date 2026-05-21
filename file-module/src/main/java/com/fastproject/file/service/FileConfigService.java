package com.fastproject.file.service;

import com.fastproject.file.vo.config.FileConfigCreate;
import com.fastproject.file.vo.config.FileConfigQuery;
import com.fastproject.file.vo.config.FileConfigUpdate;
import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 文件配置 Service 接口
 */
public interface FileConfigService {

    /**
     * 添加
     */
    Long save(FileConfigCreate create);

    /**
     * 修改
     */
    void update(FileConfigUpdate update);

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
    FileConfigVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<FileConfigVo>> findPage(FileConfigQuery query);

    /**
     * 根据类型查询配置
     */
    FileConfigVo findByType(String type);

    /**
     * 获取所有启用的配置
     */
    List<FileConfigVo> findAllEnabled();
}
