package com.fastproject.file.api;

import com.fastproject.file.vo.FileUrlVo;

import java.util.Set;

public interface FileHandle {
    /**
     * 根据id获取文件url
     * @param id 文件id
     * @return 文件url
     */
    String getUrl(String id);

    /**
     * 根据ids获取文件url
     * @param ids 文件ids
     * @return 文件url
     */
    Set<FileUrlVo> getUrls(Set<String> ids);
}
