package com.fastproject.file.storage;

import com.fastproject.file.vo.config.FileConfigVo;

import java.util.List;

/**
 * 存储配置选择器接口
 * 用于从多个配置中选择合适的存储配置
 */
public interface ConfigSelector {

    /**
     * 选择存储配置
     *
     * @param configs 可用配置列表
     * @return 选中的配置，如果没有可用配置返回 null
     */
    FileConfigVo select(List<FileConfigVo> configs);

    /**
     * 根据ID选择配置
     *
     * @param configs  可用配置列表
     * @param configId 指定配置ID
     * @return 选中的配置
     */
    default FileConfigVo selectById(List<FileConfigVo> configs, Long configId) {
        if (configId == null || configs == null || configs.isEmpty()) {
            return select(configs);
        }
        return configs.stream()
                .filter(c -> configId.equals(c.getId()))
                .findFirst()
                .orElse(select(configs));
    }
}
