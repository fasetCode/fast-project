package com.fastproject.file.storage.impl;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.PresignOptions;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.CompleteMultipartUpload;
import com.aliyun.sdk.service.oss2.models.CompleteMultipartUploadRequest;
import com.aliyun.sdk.service.oss2.models.DeleteObjectRequest;
import com.aliyun.sdk.service.oss2.models.HeadObjectRequest;
import com.aliyun.sdk.service.oss2.models.InitiateMultipartUploadRequest;
import com.aliyun.sdk.service.oss2.models.Part;
import com.aliyun.sdk.service.oss2.models.GetObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.UploadPartRequest;
import com.aliyun.sdk.service.oss2.models.UploadPartResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fastproject.exception.BusinessException;
import com.fastproject.file.storage.AbstractFileStorageStrategy;
import com.fastproject.file.storage.FileStoragePathHelper;
import com.fastproject.file.storage.vo.AlibabaFIleKeyVo;
import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.utils.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service("aliyunFileStorageStrategy")
@RequiredArgsConstructor
@Slf4j
public class AliyunFileStorageStrategyImpl extends AbstractFileStorageStrategy {
    private static final String TYPE = "aliyunFileStorageStrategy";
    private static final long DEFAULT_URL_EXPIRE_SECONDS = 3600L;

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
        try (InputStream inputStream = file.getInputStream()) {
            return saveInputStream(inputStream, config, fileName, file.getSize());
        }
    }

    @Override
    public String saveInputStream(InputStream inputStream, FileConfigVo config, String fileName, long size) throws IOException {
        AlibabaFIleKeyVo aliyunConfig = getAliyunConfig(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);

        try (OSSClient client = buildClient(aliyunConfig)) {
            client.putObject(
                    PutObjectRequest.newBuilder()
                            .bucket(aliyunConfig.getBucket())
                            .key(storageKey)
                            .body(BinaryData.fromStream(inputStream, size > 0 ? size : null))
                            .build()
            );
            log.info("阿里云 OSS 文件上传成功：bucket={}, key={}", aliyunConfig.getBucket(), storageKey);
            return getAccessUrl(config, storageKey);
        } catch (Exception e) {
            log.error("阿里云 OSS 文件上传失败：key={}", storageKey, e);
            throw new BusinessException("阿里云 OSS 文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public Path saveChunk(MultipartFile chunkFile, FileConfigVo config, String fileMd5, int chunkNumber) throws IOException {
        Path chunkPath = saveChunkToTemp(chunkFile, TYPE, fileMd5, chunkNumber);
        log.debug("阿里云 OSS 分片暂存成功：{}", chunkPath);
        return chunkPath;
    }

    @Override
    public String mergeChunks(FileConfigVo config, String fileMd5, String fileName, int totalChunks) throws IOException {
        AlibabaFIleKeyVo aliyunConfig = getAliyunConfig(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);
        Path chunkDir = getChunkDir(TYPE, fileMd5);
        validateChunksExist(chunkDir, totalChunks);

        try (OSSClient client = buildClient(aliyunConfig)) {
            String uploadId = client.initiateMultipartUpload(
                    InitiateMultipartUploadRequest.newBuilder()
                            .bucket(aliyunConfig.getBucket())
                            .key(storageKey)
                            .build()
            ).initiateMultipartUpload().uploadId();

            List<Part> parts = new ArrayList<>(totalChunks);
            for (int i = 0; i < totalChunks; i++) {
                Path chunkPath = chunkDir.resolve("chunk_" + i);
                byte[] content = Files.readAllBytes(chunkPath);
                long partNumber = i + 1L;

                UploadPartResult partResult = client.uploadPart(
                        UploadPartRequest.newBuilder()
                                .bucket(aliyunConfig.getBucket())
                                .key(storageKey)
                                .uploadId(uploadId)
                                .partNumber(partNumber)
                                .contentLength((long) content.length)
                                .body(BinaryData.fromBytes(content))
                                .build()
                );

                parts.add(Part.newBuilder()
                        .partNumber(partNumber)
                        .eTag(partResult.eTag())
                        .build());
            }

            client.completeMultipartUpload(
                    CompleteMultipartUploadRequest.newBuilder()
                            .bucket(aliyunConfig.getBucket())
                            .key(storageKey)
                            .uploadId(uploadId)
                            .completeMultipartUpload(CompleteMultipartUpload.newBuilder().parts(parts).build())
                            .build()
            );

            log.info("阿里云 OSS 分片合并成功：bucket={}, key={}, chunks={}",
                    aliyunConfig.getBucket(), storageKey, totalChunks);
            return getAccessUrl(config, storageKey);
        } catch (Exception e) {
            log.error("阿里云 OSS 分片合并失败：key={}", storageKey, e);
            throw new BusinessException("阿里云 OSS 分片合并失败：" + e.getMessage());
        } finally {
            cleanupChunks(chunkDir);
        }
    }

    @Override
    public boolean deleteFile(FileConfigVo config, String fileName) {
        AlibabaFIleKeyVo aliyunConfig = getAliyunConfig(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);

        try (OSSClient client = buildClient(aliyunConfig)) {
            client.deleteObject(DeleteObjectRequest.newBuilder()
                    .bucket(aliyunConfig.getBucket())
                    .key(storageKey)
                    .build());
            log.info("阿里云 OSS 文件删除成功：bucket={}, key={}", aliyunConfig.getBucket(), storageKey);
            return true;
        } catch (Exception e) {
            log.error("阿里云 OSS 文件删除失败：key={}", storageKey, e);
            return false;
        }
    }

    @Override
    public String getAccessUrl(FileConfigVo config, String fileName) {
        AlibabaFIleKeyVo aliyunConfig = getAliyunConfig(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);
        if (Boolean.TRUE.equals(aliyunConfig.getPrivateBucket())) {
            return buildPresignedUrl(aliyunConfig, storageKey);
        }

        String relativePath = fileStoragePathHelper.normalizeRelativePath(storageKey);
        if (StringUtils.hasText(config.getAccessDomain())) {
            return fileStoragePathHelper.joinPrefix(config.getAccessDomain(), relativePath);
        }
        if (StringUtils.hasText(config.getRemoteUrl())) {
            return fileStoragePathHelper.joinPrefix(config.getRemoteUrl(), relativePath);
        }
        return fileStoragePathHelper.joinPrefix(buildDefaultAccessPrefix(aliyunConfig), relativePath);
    }

    @Override
    public boolean exists(FileConfigVo config, String fileName) {
        AlibabaFIleKeyVo aliyunConfig = getAliyunConfig(config);
        String storageKey = fileStoragePathHelper.getStorageKey(fileName);

        try (OSSClient client = buildClient(aliyunConfig)) {
            client.headObject(HeadObjectRequest.newBuilder()
                    .bucket(aliyunConfig.getBucket())
                    .key(storageKey)
                    .build());
            return true;
        } catch (Exception e) {
            if (isObjectMissing(e)) {
                return false;
            }
            log.error("阿里云 OSS 文件存在性检查失败：key={}", storageKey, e);
            return false;
        }
    }

    private OSSClient buildClient(AlibabaFIleKeyVo aliyunConfig) {
        CredentialsProvider credentialsProvider = new StaticCredentialsProvider(
                aliyunConfig.getAccessKeyId(),
                aliyunConfig.getAccessKeySecret()
        );

        return OSSClient.newBuilder()
                .endpoint(aliyunConfig.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .region(aliyunConfig.getRegion())
                .build();
    }

    private AlibabaFIleKeyVo getAliyunConfig(FileConfigVo config) {
        if (config == null) {
            throw new BusinessException("文件存储配置不能为空");
        }
        if (!StringUtils.hasText(config.getConfig())) {
            throw new BusinessException("阿里云 OSS 配置不能为空");
        }

        AlibabaFIleKeyVo aliyunConfig = JsonUtils.fromJson(config.getConfig(), AlibabaFIleKeyVo.class);
        if (aliyunConfig == null
                || !StringUtils.hasText(aliyunConfig.getAccessKeyId())
                || !StringUtils.hasText(aliyunConfig.getAccessKeySecret())
                || !StringUtils.hasText(aliyunConfig.getEndpoint())
                || !StringUtils.hasText(aliyunConfig.getRegion())
                || !StringUtils.hasText(aliyunConfig.getBucket())) {
            throw new BusinessException("阿里云 OSS 配置不完整");
        }
        return aliyunConfig;
    }

    private String buildDefaultAccessPrefix(AlibabaFIleKeyVo aliyunConfig) {
        String endpoint = aliyunConfig.getEndpoint().trim();
        if (endpoint.startsWith("http://") || endpoint.startsWith("https://")) {
            String protocol = endpoint.startsWith("https://") ? "https://" : "http://";
            String host = endpoint.substring(protocol.length());
            if (!host.startsWith(aliyunConfig.getBucket() + ".")) {
                host = aliyunConfig.getBucket() + "." + host;
            }
            return protocol + host;
        }

        if (endpoint.startsWith(aliyunConfig.getBucket() + ".")) {
            return "https://" + endpoint;
        }
        return "https://" + aliyunConfig.getBucket() + "." + endpoint;
    }

    private String buildPresignedUrl(AlibabaFIleKeyVo aliyunConfig, String storageKey) {
        try (OSSClient client = buildClient(aliyunConfig)) {
            PresignOptions options = PresignOptions.newBuilder()
                    .expiration(Instant.now().plusSeconds(resolveExpireSeconds(aliyunConfig)))
                    .build();
            return client.presign(
                    GetObjectRequest.newBuilder()
                            .bucket(aliyunConfig.getBucket())
                            .key(storageKey)
                            .build(),
                    options
            ).url();
        } catch (Exception e) {
            log.error("阿里云 OSS 生成临时访问地址失败：key={}", storageKey, e);
            throw new BusinessException("阿里云 OSS 生成临时访问地址失败：" + e.getMessage());
        }
    }

    private long resolveExpireSeconds(AlibabaFIleKeyVo aliyunConfig) {
        Long expireSeconds = aliyunConfig.getUrlExpireSeconds();
        return expireSeconds == null || expireSeconds <= 0 ? DEFAULT_URL_EXPIRE_SECONDS : expireSeconds;
    }

    private boolean isObjectMissing(Exception e) {
        String message = e.getMessage();
        return message != null && (message.contains("404") || message.contains("Not Found") || message.contains("NoSuchKey"));
    }
}
