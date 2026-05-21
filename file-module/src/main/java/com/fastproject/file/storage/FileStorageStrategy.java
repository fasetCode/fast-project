package com.fastproject.file.storage;

import com.fastproject.file.vo.config.FileConfigVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * 文件存储策略接口
 * 支持本地存储、远程存储等多种存储方式
 */
public interface FileStorageStrategy {

    /**
     * 获取策略类型
     *
     * @return 类型标识，如 "local", "oss", "cos" 等
     */
    String getType();

    /**
     * 是否支持该配置类型
     *
     * @param config 存储配置
     * @return true 支持，false 不支持
     */
    boolean supports(FileConfigVo config);

    /**
     * 保存文件
     *
     * @param file     文件
     * @param config   存储配置
     * @param fileName 存储文件名（包含路径）
     * @return 访问路径
     * @throws IOException IO异常
     */
    String saveFile(MultipartFile file, FileConfigVo config, String fileName) throws IOException;

    /**
     * 保存输入流
     *
     * @param inputStream 输入流
     * @param config      存储配置
     * @param fileName    存储文件名
     * @param size        文件大小
     * @return 访问路径
     * @throws IOException IO异常
     */
    String saveInputStream(InputStream inputStream, FileConfigVo config, String fileName, long size) throws IOException;

    /**
     * 保存分片文件
     *
     * @param chunkFile   分片文件
     * @param config      存储配置
     * @param fileMd5     文件MD5
     * @param chunkNumber 分片序号
     * @return 临时分片路径
     * @throws IOException IO异常
     */
    Path saveChunk(MultipartFile chunkFile, FileConfigVo config, String fileMd5, int chunkNumber) throws IOException;

    /**
     * 合并分片
     *
     * @param config      存储配置
     * @param fileMd5     文件MD5
     * @param fileName    最终文件名
     * @param totalChunks 总分片数
     * @return 访问路径
     * @throws IOException IO异常
     */
    String mergeChunks(FileConfigVo config, String fileMd5, String fileName, int totalChunks) throws IOException;

    /**
     * 删除文件
     *
     * @param config   存储配置
     * @param fileName 文件名
     * @return true 删除成功
     */
    boolean deleteFile(FileConfigVo config, String fileName);

    /**
     * 获取文件访问URL
     *
     * @param config   存储配置
     * @param fileName 文件名
     * @return 完整访问URL
     */
    String getAccessUrl(FileConfigVo config, String fileName);

    /**
     * 检查文件是否存在
     *
     * @param config   存储配置
     * @param fileName 文件名
     * @return true 存在
     */
    boolean exists(FileConfigVo config, String fileName);
}
