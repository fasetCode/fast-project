package com.fastproject.file.vo.upload;

import lombok.Data;

import java.util.List;

/**
 * 分片检查响应
 */
@Data
public class ChunkCheckResponse {

    /**
     * 是否需要重新上传（false表示文件已存在，秒传）
     */
    private Boolean needUpload;

    /**
     * 文件ID（如果文件已存在）
     */
    private Long fileId;

    /**
     * 已上传的分片序号列表
     */
    private List<Integer> uploadedChunks;

    /**
     * 总分片数
     */
    private Integer totalChunks;

    public static ChunkCheckResponse needUpload(List<Integer> uploadedChunks, Integer totalChunks) {
        ChunkCheckResponse response = new ChunkCheckResponse();
        response.setNeedUpload(true);
        response.setUploadedChunks(uploadedChunks);
        response.setTotalChunks(totalChunks);
        return response;
    }

    public static ChunkCheckResponse existed(Long fileId) {
        ChunkCheckResponse response = new ChunkCheckResponse();
        response.setNeedUpload(false);
        response.setFileId(fileId);
        return response;
    }
}
