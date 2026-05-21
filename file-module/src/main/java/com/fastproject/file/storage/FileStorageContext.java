package com.fastproject.file.storage;

import com.fastproject.exception.BusinessException;
import com.fastproject.file.vo.config.FileConfigVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件存储上下文
 * 管理存储策略和配置选择
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileStorageContext {

    private final List<FileStorageStrategy> strategies;
    private final ConfigSelector configSelector;

    // 缓存配置
    private final Map<Long, FileConfigVo> configCache = new ConcurrentHashMap<>();

    /**
     * 获取存储策略
     *
     * @param config 存储配置
     * @return 存储策略
     */
    public FileStorageStrategy getStrategy(FileConfigVo config) {
        if (config == null) {
            throw new BusinessException("存储配置不能为空");
        }

        return strategies.stream()
                .filter(s -> s.supports(config))
                .findFirst()
                .orElseThrow(() -> new BusinessException("不支持的存储类型: " + config.getType()));
    }

    /**
     * 选择配置
     *
     * @param configs 配置列表
     * @return 选中的配置
     */
    public FileConfigVo selectConfig(List<FileConfigVo> configs) {
        return configSelector.select(configs);
    }

    /**
     * 根据ID选择配置
     *
     * @param configs  配置列表
     * @param configId 配置ID
     * @return 选中的配置
     */
    public FileConfigVo selectConfigById(List<FileConfigVo> configs, Long configId) {
        return configSelector.selectById(configs, configId);
    }

    /**
     * 保存文件
     */
    public String saveFile(org.springframework.web.multipart.MultipartFile file,
                           FileConfigVo config, String fileName) throws Exception {
        FileStorageStrategy strategy = getStrategy(config);
        return strategy.saveFile(file, config, fileName);
    }

    /**
     * 保存输入流
     */
    public String saveInputStream(InputStream inputStream, FileConfigVo config,
                                  String fileName, long size) throws Exception {
        FileStorageStrategy strategy = getStrategy(config);
        return strategy.saveInputStream(inputStream, config, fileName, size);
    }

    /**
     * 保存分片
     */
    public Path saveChunk(org.springframework.web.multipart.MultipartFile chunkFile,
                          FileConfigVo config, String fileMd5, int chunkNumber) throws Exception {
        FileStorageStrategy strategy = getStrategy(config);
        return strategy.saveChunk(chunkFile, config, fileMd5, chunkNumber);
    }

    /**
     * 合并分片
     */
    public String mergeChunks(FileConfigVo config, String fileMd5,
                              String fileName, int totalChunks) throws Exception {
        FileStorageStrategy strategy = getStrategy(config);
        return strategy.mergeChunks(config, fileMd5, fileName, totalChunks);
    }

    /**
     * 删除文件
     */
    public boolean deleteFile(FileConfigVo config, String fileName) {
        FileStorageStrategy strategy = getStrategy(config);
        return strategy.deleteFile(config, fileName);
    }

    /**
     * 获取访问URL
     */
    public String getAccessUrl(FileConfigVo config, String fileName) {
        FileStorageStrategy strategy = getStrategy(config);
        return strategy.getAccessUrl(config, fileName);
    }

    /**
     * 检查文件是否存在
     */
    public boolean exists(FileConfigVo config, String fileName) {
        FileStorageStrategy strategy = getStrategy(config);
        return strategy.exists(config, fileName);
    }
}
