package com.fastproject.file.vo.upload;

import lombok.Data;

/**
 * 文件上传响应
 */
@Data
public class FileUploadResponse {

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件MD5
     */
    private String fileMd5;

    /**
     * 访问路径
     */
    private String accessPath;

    /**
     * 是否已经存在（秒传）
     */
    private Boolean existed;

    /**
     * 已上传的分片列表（用于断点续传）
     */
    private java.util.List<Integer> uploadedChunks;

    public FileUploadResponse() {
    }

    public FileUploadResponse(Long fileId, String fileName, Long fileSize, String fileMd5, String accessPath) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileMd5 = fileMd5;
        this.accessPath = accessPath;
        this.existed = false;
    }

    public static FileUploadResponse ofExisted(Long fileId, String fileName, Long fileSize, String fileMd5, String accessPath) {
        FileUploadResponse response = new FileUploadResponse(fileId, fileName, fileSize, fileMd5, accessPath);
        response.setExisted(true);
        return response;
    }
}
