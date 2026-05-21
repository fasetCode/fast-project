package com.fastproject.file.vo.upload;

import lombok.Data;

/**
 * 文件分片合并请求
 */
@Data
public class FileChunkMergeRequest {

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
     * 总分片数
     */
    private Integer totalChunks;

    /**
     * 配置ID（可选，指定存储配置）
     */
    private Long configId;
}
