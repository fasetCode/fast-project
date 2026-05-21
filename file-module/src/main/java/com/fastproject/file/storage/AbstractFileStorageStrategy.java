package com.fastproject.file.storage;

import com.fastproject.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 提供分片临时目录等通用能力，便于扩展更多存储策略。
 */
public abstract class AbstractFileStorageStrategy implements FileStorageStrategy {

    protected Path saveChunkToTemp(MultipartFile chunkFile, String strategyType, String fileMd5, int chunkNumber) throws IOException {
        Path chunkDir = getChunkDir(strategyType, fileMd5);
        Files.createDirectories(chunkDir);

        Path chunkPath = chunkDir.resolve("chunk_" + chunkNumber);
        chunkFile.transferTo(chunkPath.toFile());
        return chunkPath;
    }

    protected Path getChunkDir(String strategyType, String fileMd5) {
        return Paths.get(System.getProperty("java.io.tmpdir"), "upload_chunks", fileMd5);
    }

    protected void validateChunksExist(Path chunkDir, int totalChunks) {
        for (int i = 0; i < totalChunks; i++) {
            Path chunkPath = chunkDir.resolve("chunk_" + i);
            if (!Files.exists(chunkPath)) {
                throw new BusinessException("分片 " + i + " 不存在，无法合并");
            }
        }
    }

    protected void cleanupChunks(Path chunkDir) {
        try {
            if (!Files.exists(chunkDir)) {
                return;
            }

            try (Stream<Path> paths = Files.list(chunkDir)) {
                paths.forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException ignored) {
                    }
                });
            }

            Files.deleteIfExists(chunkDir);
        } catch (IOException ignored) {
        }
    }
}
