package com.fastproject.file.service;

import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.file.vo.upload.ChunkCheckResponse;
import com.fastproject.file.vo.upload.FileChunkMergeRequest;
import com.fastproject.file.vo.upload.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传服务接口
 */
public interface FileUploadService {

    /**
     * 小文件直接上传
     *
     * @param file     文件
     * @param configId 指定配置ID（可选）
     * @return 上传结果
     */
    FileUploadResponse uploadSimple(MultipartFile file, Long configId);

    /**
     * 检查分片上传状态
     *
     * @param fileMd5  文件MD5
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 分片检查响应
     */
    ChunkCheckResponse checkChunk(String fileMd5, Long fileSize, String fileName);

    /**
     * 上传分片
     *
     * @param chunkFile   分片文件
     * @param fileMd5     文件MD5
     * @param fileName    文件名
     * @param fileSize    文件大小
     * @param fileType    文件类型
     * @param chunkNumber 分片序号
     * @param totalChunks 总分片数
     * @param chunkSize   分片大小
     * @param configId    配置ID
     * @return 上传结果
     */
    FileUploadResponse uploadChunk(MultipartFile chunkFile, String fileMd5, String fileName,
                                   Long fileSize, String fileType, Integer chunkNumber,
                                   Integer totalChunks, Long chunkSize, Long configId);

    /**
     * 合并分片
     *
     * @param request 合并请求
     * @return 合并结果
     */
    FileUploadResponse mergeChunks(FileChunkMergeRequest request);

    /**
     * 获取所有可用配置
     *
     * @return 配置列表
     */
    List<FileConfigVo> getAvailableConfigs();
}
