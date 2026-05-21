package com.fastproject.file.service.impl;

import com.fastproject.exception.BusinessException;
import com.fastproject.file.domain.FileType;
import com.fastproject.file.repository.db.FileTypeRepository;
import com.fastproject.file.service.FileConfigService;
import com.fastproject.file.service.FileInfoService;
import com.fastproject.file.service.FileUploadService;
import com.fastproject.file.storage.FileStorageContext;
import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.file.vo.info.FileInfoCreate;
import com.fastproject.file.vo.info.FileInfoVo;
import com.fastproject.file.vo.upload.ChunkCheckResponse;
import com.fastproject.file.vo.upload.FileChunkMergeRequest;
import com.fastproject.file.vo.upload.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件上传服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileInfoService fileInfoService;
    private final FileConfigService fileConfigService;
    private final FileStorageContext storageContext;
    private final FileTypeRepository fileTypeRepository;

    // 分片大小：5MB
    private static final long DEFAULT_CHUNK_SIZE = 5 * 1024 * 1024;

    // 临时存储分片信息
    private final Map<String, Set<Integer>> chunkCache = new ConcurrentHashMap<>();

    @Override
    public FileUploadResponse uploadSimple(MultipartFile file, Long configId) {
        log.info("小文件上传：文件名={}, 大小={}, configId={}",
                file.getOriginalFilename(), file.getSize(), configId);

        try {
            // 1. 检查秒传
            String fileMd5 = calculateMd5(file);
            FileInfoVo existedFile = fileInfoService.findByFileMd5(fileMd5);
            if (existedFile != null) {
                log.info("文件秒传：fileMd5={}", fileMd5);
                return FileUploadResponse.ofExisted(
                        existedFile.getId(),
                        existedFile.getFileName(),
                        existedFile.getFileSize(),
                        existedFile.getFileMd5(),
                        existedFile.getAccessPath()
                );
            }

            // 2. 获取存储配置
            FileConfigVo config = getConfig(configId);

            // 3. 生成文件名和路径
            String fileName = generateFileName(file.getOriginalFilename(), fileMd5);
            String filePath = "/" + fileName;

            // 4. 保存文件
            String accessPath = storageContext.saveFile(file, config, fileName);

            // 5. 获取文件类型
            String extension = getFileExtension(file.getOriginalFilename());
            Long fileTypeId = getFileTypeId(extension);

            // 6. 保存文件信息
            FileInfoCreate create = new FileInfoCreate();
            create.setFileName(file.getOriginalFilename());
            create.setFileSize(file.getSize());
            create.setFileType(extension);
            create.setFileMd5(fileMd5);
            create.setFileStorage(config.getType());
            create.setAccessPath(accessPath);
            create.setFilePath(filePath);
            create.setConfigId(config.getId());
            create.setFileTypeId(fileTypeId);
            create.setStatus(1);

            Long fileId = fileInfoService.save(create);

            return new FileUploadResponse(fileId, create.getFileName(),
                    create.getFileSize(), fileMd5, accessPath);

        } catch (Exception e) {
            log.error("小文件上传失败", e);
            throw new BusinessException("文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public ChunkCheckResponse checkChunk(String fileMd5, Long fileSize, String fileName) {
        log.info("分片上传检查：fileMd5={}, fileSize={}, fileName={}", fileMd5, fileSize, fileName);

        // 1. 检查秒传
        FileInfoVo existedFile = fileInfoService.findByFileMd5(fileMd5);
        if (existedFile != null) {
            log.info("文件秒传：fileMd5={}", fileMd5);
            return ChunkCheckResponse.existed(existedFile.getId());
        }

        // 2. 计算总分片数
        int totalChunks = (int) Math.ceil((double) fileSize / DEFAULT_CHUNK_SIZE);

        // 3. 检查已上传分片
        Set<Integer> uploadedChunks = getUploadedChunks(fileMd5);

        log.info("分片检查完成：已上传 {}/{} 分片", uploadedChunks.size(), totalChunks);

        return ChunkCheckResponse.needUpload(new ArrayList<>(uploadedChunks), totalChunks);
    }

    @Override
    public FileUploadResponse uploadChunk(MultipartFile chunkFile, String fileMd5, String fileName,
                                          Long fileSize, String fileType, Integer chunkNumber,
                                          Integer totalChunks, Long chunkSize, Long configId) {
        log.info("上传分片：fileMd5={}, chunkNumber={}, totalChunks={}",
                fileMd5, chunkNumber, totalChunks);

        try {
            // 获取配置
            FileConfigVo config = getConfig(configId);

            // 保存分片
            storageContext.saveChunk(chunkFile, config, fileMd5, chunkNumber);

            // 记录已上传
            chunkCache.computeIfAbsent(fileMd5, k -> ConcurrentHashMap.newKeySet())
                    .add(chunkNumber);

            FileUploadResponse response = new FileUploadResponse();
            response.setFileMd5(fileMd5);
            response.setFileName(fileName);
            return response;

        } catch (Exception e) {
            log.error("分片上传失败", e);
            throw new BusinessException("分片上传失败：" + e.getMessage());
        }
    }

    @Override
    public FileUploadResponse mergeChunks(FileChunkMergeRequest request) {
        log.info("合并分片：fileMd5={}, fileName={}, totalChunks={}",
                request.getFileMd5(), request.getFileName(), request.getTotalChunks());

        try {
            // 1. 检查所有分片
            Set<Integer> uploadedChunks = getUploadedChunks(request.getFileMd5());
            for (int i = 0; i < request.getTotalChunks(); i++) {
                if (!uploadedChunks.contains(i)) {
                    throw new BusinessException("分片 " + i + " 未上传，无法合并");
                }
            }

            // 2. 获取配置
            FileConfigVo config = getConfig(request.getConfigId());

            // 3. 生成文件名和路径
            String fileName = generateFileName(request.getFileName(), request.getFileMd5());
            String filePath = "/" + fileName;

            // 4. 合并分片
            String accessPath = storageContext.mergeChunks(config, request.getFileMd5(),
                    fileName, request.getTotalChunks());

            // 5. 获取文件类型
            String extension = getFileExtension(request.getFileName());
            Long fileTypeId = getFileTypeId(extension);

            // 6. 保存文件信息
            FileInfoCreate create = new FileInfoCreate();
            create.setFileName(request.getFileName());
            create.setFileSize(request.getFileSize());
            create.setFileType(extension);
            create.setFileMd5(request.getFileMd5());
            create.setFileStorage(config.getType());
            create.setAccessPath(accessPath);
            create.setFilePath(filePath);
            create.setConfigId(config.getId());
            create.setFileTypeId(fileTypeId);
            create.setStatus(1);

            Long fileId = fileInfoService.save(create);

            // 6. 清理缓存
            chunkCache.remove(request.getFileMd5());

            return new FileUploadResponse(fileId, create.getFileName(),
                    create.getFileSize(), request.getFileMd5(), accessPath);

        } catch (Exception e) {
            log.error("分片合并失败", e);
            throw new BusinessException("分片合并失败：" + e.getMessage());
        }
    }

    @Override
    public List<FileConfigVo> getAvailableConfigs() {
        // 获取所有启用的配置
        return fileConfigService.findPage(
                new com.fastproject.file.vo.config.FileConfigQuery() {{
                    setPage(0);
                    setPageSize(100);
                }}
        ).getData();
    }

    /**
     * 获取存储配置
     */
    private FileConfigVo getConfig(Long configId) {
        List<FileConfigVo> configs = getAvailableConfigs();
        if (configs.isEmpty()) {
            throw new BusinessException("没有可用的存储配置");
        }
        return storageContext.selectConfigById(configs, configId);
    }

    /**
     * 获取已上传的分片
     */
    private Set<Integer> getUploadedChunks(String fileMd5) {
        Set<Integer> uploadedChunks = new HashSet<>();

        // 从缓存获取
        Set<Integer> cached = chunkCache.get(fileMd5);
        if (cached != null) {
            uploadedChunks.addAll(cached);
        }

        // 从文件系统获取
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            Path chunkDir = Paths.get(tempDir, "upload_chunks", fileMd5);
            if (Files.exists(chunkDir)) {
                try (Stream<Path> paths = Files.list(chunkDir)) {
                    Set<Integer> fileChunks = paths
                            .filter(p -> p.getFileName().toString().startsWith("chunk_"))
                            .map(p -> {
                                try {
                                    return Integer.parseInt(p.getFileName().toString().substring(6));
                                } catch (NumberFormatException e) {
                                    return -1;
                                }
                            })
                            .filter(n -> n >= 0)
                            .collect(Collectors.toSet());
                    uploadedChunks.addAll(fileChunks);
                }
            }
        } catch (IOException e) {
            log.warn("读取已上传分片失败", e);
        }

        return uploadedChunks;
    }

    /**
     * 计算MD5
     */
    private String calculateMd5(MultipartFile file) throws IOException {
        return org.springframework.util.DigestUtils.md5DigestAsHex(file.getInputStream());
    }

    /**
     * 生成文件名
     */
    private String generateFileName(String originalFilename, String md5) {
        String extension = getFileExtension(originalFilename);
        String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        return dateDir + "/" + md5 + (extension.isEmpty() ? "" : "." + extension);
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * 获取文件类型ID（如果不存在则自动创建）
     */
    private Long getFileTypeId(String extension) {
        if (extension == null || extension.isEmpty()) {
            return null;
        }
        try {
            String typeName = extension.toUpperCase();
            FileType fileType = fileTypeRepository.findByName(typeName);
            
            if (fileType != null) {
                log.debug("找到文件类型：{}，ID：{}", typeName, fileType.getId());
                return fileType.getId();
            }
            
            // 自动创建文件类型
            log.info("文件类型不存在，自动创建：{}", typeName);
            FileType newType = new FileType();
            newType.setName(typeName);
            newType.setPlatformRatio(0.0);  // 默认平台占比
            newType.setFileSpace(0.0);      // 默认文件空间
            fileTypeRepository.save(newType);
            
            log.info("文件类型创建成功：{}，ID：{}", typeName, newType.getId());
            return newType.getId();
            
        } catch (Exception e) {
            log.error("获取/创建文件类型失败：{}", extension, e);
            return null;
        }
    }
}
