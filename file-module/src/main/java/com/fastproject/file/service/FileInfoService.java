package com.fastproject.file.service;

import com.fastproject.file.vo.info.FileInfoCreate;
import com.fastproject.file.vo.info.FileInfoQuery;
import com.fastproject.file.vo.info.FileInfoUpdate;
import com.fastproject.file.vo.info.FileInfoVo;
import com.fastproject.file.vo.info.FileTypeStatVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 文件信息 Service 接口
 */
public interface FileInfoService {

    /**
     * 添加
     */
    Long save(FileInfoCreate create);

    /**
     * 获取文件类型统计
     */
    List<FileTypeStatVo> getTypeStats();

    /**
     * 修改
     */
    void update(FileInfoUpdate update);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 批量删除
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询
     */
    FileInfoVo findById(Long id);

    /**
     * 根据ID列表批量查询
     */
    List<FileInfoVo> findByIds(List<Long> ids);

    /**
     * 分页查询
     */
    PageVo<List<FileInfoVo>> findPage(FileInfoQuery query);

    /**
     * 根据文件MD5查询
     */
    FileInfoVo findByFileMd5(String fileMd5);
}
