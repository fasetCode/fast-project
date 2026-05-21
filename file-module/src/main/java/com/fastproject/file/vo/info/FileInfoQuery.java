package com.fastproject.file.vo.info;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件信息查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileInfoQuery extends PageQuery {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件状态
     */
    private Integer status;

    /**
     * 文件md5
     */
    private String fileMd5;
}
