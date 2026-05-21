package com.fastproject.file.vo.upload;

import lombok.Data;

/**
 * 文件分片上传请求
 */
@Data
public class FileChunkUploadRequest {

    /**
     * 文件唯一标识（MD5）
     */
    private String fileMd5;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 当前分片序号（从0开始）
     */
    private Integer chunkNumber;

    /**
     * 总分片数
     */
    private Integer totalChunks;

    /**
     * 当前分片大小
     */
    private Long chunkSize;

    /**
     * 配置ID（可选，指定存储配置）
     */
    private Long configId;
}
