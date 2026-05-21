package com.fastproject.file.vo.info;

import lombok.Data;

/**
 * 文件信息更新
 */
@Data
public class FileInfoUpdate {

    /**
     * 文件ID
     */
    private Long id;

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
     * 文件md5
     */
    private String fileMd5;

    /**
     * 文件状态
     */
    private Integer status;

    /**
     * 文件存储位置
     */
    private String fileStorage;

    /**
     * 访问路径
     */
    private String accessPath;

    /**
     * 文件路径（如 /2026/04/01/64788981cb124e798613047ab4e34aa9.jpg）
     */
    private String filePath;

    /**
     * 配置id
     */
    private Long configId;

    /**
     * 文件类型id
     */
    private Long fileTypeId;
}
