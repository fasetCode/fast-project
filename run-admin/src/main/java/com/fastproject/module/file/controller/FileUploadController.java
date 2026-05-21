package com.fastproject.module.file.controller;

import com.fastproject.file.service.FileUploadService;
import com.fastproject.file.vo.upload.ChunkCheckResponse;
import com.fastproject.file.vo.upload.FileChunkMergeRequest;
import com.fastproject.file.vo.upload.FileUploadResponse;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传 Controller
 * 支持小文件直接上传和大文件分片上传
 */
@Slf4j
@RestController
@RequestMapping("/file/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    /**
     * 小文件直接上传
     *
     * @param file     文件
     * @param configId 配置ID（可选）
     * @return 文件上传结果
     */
    @PostMapping("/simple")
    @PreAuthorize("@ps.hasPermission('admin:file:upload:simple')")
    public ResultVo<FileUploadResponse> uploadSimple(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "configId", required = false) Long configId) {
        return ResultVo.success(fileUploadService.uploadSimple(file, configId));
    }

    /**
     * 大文件分片上传 - 初始化检查
     * 检查文件是否已存在，返回已上传的分片列表
     *
     * @param fileMd5  文件MD5
     * @param fileSize 文件大小
     * @param fileName 文件名
     * @return 分片检查响应
     */
    @GetMapping("/chunk/check")
    @PreAuthorize("@ps.hasPermission('admin:file:upload:chunk')")
    public ResultVo<ChunkCheckResponse> checkChunk(
            @RequestParam("fileMd5") String fileMd5,
            @RequestParam("fileSize") Long fileSize,
            @RequestParam("fileName") String fileName) {
        return ResultVo.success(fileUploadService.checkChunk(fileMd5, fileSize, fileName));
    }

    /**
     * 大文件分片上传 - 上传单个分片
     */
    @PostMapping("/chunk")
    @PreAuthorize("@ps.hasPermission('admin:file:upload:chunk')")
    public ResultVo<FileUploadResponse> uploadChunk(
            @RequestParam("file") MultipartFile chunkFile,
            @RequestParam("fileMd5") String fileMd5,
            @RequestParam("fileName") String fileName,
            @RequestParam("fileSize") Long fileSize,
            @RequestParam("fileType") String fileType,
            @RequestParam("chunkNumber") Integer chunkNumber,
            @RequestParam("totalChunks") Integer totalChunks,
            @RequestParam("chunkSize") Long chunkSize,
            @RequestParam(value = "configId", required = false) Long configId) {

        return ResultVo.success(fileUploadService.uploadChunk(
                chunkFile, fileMd5, fileName, fileSize, fileType,
                chunkNumber, totalChunks, chunkSize, configId));
    }

    /**
     * 大文件分片上传 - 合并分片
     *
     * @param request 合并请求
     * @return 合并结果
     */
    @PostMapping("/chunk/merge")
    @PreAuthorize("@ps.hasPermission('admin:file:upload:chunk')")
    public ResultVo<FileUploadResponse> mergeChunks(@RequestBody FileChunkMergeRequest request) {
        return ResultVo.success(fileUploadService.mergeChunks(request));
    }
}
