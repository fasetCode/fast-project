package com.fastproject.file.storage.impl;

import com.fastproject.exception.BusinessException;
import com.fastproject.file.storage.AbstractFileStorageStrategy;
import com.fastproject.file.storage.FileStoragePathHelper;
import com.fastproject.file.vo.config.FileConfigVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * 本地文件存储策略
 */
@Slf4j
@Service("localFileStorageStrategy")
@RequiredArgsConstructor
public class LocalFileStorageStrategyImpl extends AbstractFileStorageStrategy {

    private static final String TYPE = "localFileStorageStrategy";

    private final FileStoragePathHelper fileStoragePathHelper;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean supports(FileConfigVo config) {
        return config != null && TYPE.equals(config.getType());
    }

    @Override
    public String saveFile(MultipartFile file, FileConfigVo config, String fileName) throws IOException {
        String storagePath = getStoragePath(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);
        File destFile = new File(storagePath + storageKey);

        // 创建父目录
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        // 保存文件
        file.transferTo(destFile);
        log.info("本地文件保存成功：{}", destFile.getAbsolutePath());

        return getAccessUrl(config, storageKey);
    }

    @Override
    public String saveInputStream(InputStream inputStream, FileConfigVo config, String fileName, long size) throws IOException {
        String storagePath = getStoragePath(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);
        Path destPath = Paths.get(storagePath + storageKey);

        // 创建父目录
        if (!destPath.getParent().toFile().exists()) {
            destPath.getParent().toFile().mkdirs();
        }

        // 保存文件
        Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
        log.info("本地文件流保存成功：{}", destPath);

        return getAccessUrl(config, storageKey);
    }

    @Override
    public Path saveChunk(MultipartFile chunkFile, FileConfigVo config, String fileMd5, int chunkNumber) throws IOException {
        Path chunkPath = saveChunkToTemp(chunkFile, TYPE, fileMd5, chunkNumber);
        log.debug("分片保存成功：{}", chunkPath);
        return chunkPath;
    }

    @Override
    public String mergeChunks(FileConfigVo config, String fileMd5, String fileName, int totalChunks) throws IOException {
        String storagePath = getStoragePath(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);
        File destFile = new File(storagePath + storageKey);

        // 创建父目录
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        // 获取临时分片目录
        Path chunkDir = getChunkDir(TYPE, fileMd5);

        // 检查所有分片是否存在
        validateChunksExist(chunkDir, totalChunks);

        // 合并分片
        try (RandomAccessFile accessFile = new RandomAccessFile(destFile, "rw")) {
            for (int i = 0; i < totalChunks; i++) {
                Path chunkPath = chunkDir.resolve("chunk_" + i);
                byte[] bytes = Files.readAllBytes(chunkPath);
                accessFile.write(bytes);
            }
        }

        log.info("本地文件合并成功：{}，大小：{} bytes", destFile.getAbsolutePath(), destFile.length());

        // 清理临时分片
        cleanupChunks(chunkDir);

        return getAccessUrl(config, storageKey);
    }

    @Override
    public boolean deleteFile(FileConfigVo config, String fileName) {
        try {
            String storagePath = getStoragePath(config);
            String storageKey = fileStoragePathHelper.getStorageKey(fileName);
            File file = new File(storagePath + storageKey);
            if (file.exists()) {
                boolean deleted = file.delete();
                log.info("本地文件删除：{}，结果：{}", storageKey, deleted);
                return deleted;
            }
            return true;
        } catch (Exception e) {
            log.error("删除本地文件失败：" + fileName, e);
            return false;
        }
    }

    @Override
    public String getAccessUrl(FileConfigVo config, String fileName) {
        String relativePath = fileStoragePathHelper.normalizeRelativePath(fileName);
        if (StringUtils.hasText(config.getAccessDomain())) {
            return fileStoragePathHelper.joinPrefix(config.getAccessDomain(), relativePath);
        }
        return fileStoragePathHelper.joinPrefix("/uploads", relativePath);
    }

    @Override
    public boolean exists(FileConfigVo config, String fileName) {
        String storagePath = getStoragePath(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);
        return new File(storagePath + storageKey).exists();
    }

    /**
     * 获取存储路径
     */
    private String getStoragePath(FileConfigVo config) {
        String path = config.getStoragePath();
        if (path == null || path.isEmpty()) {
            throw new BusinessException("存储路径不能为空");
        }
        if (!path.endsWith("/") && !path.endsWith("\\")) {
            path += "/";
        }
        return path;
    }

}
