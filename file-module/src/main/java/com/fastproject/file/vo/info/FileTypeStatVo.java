package com.fastproject.file.vo.info;

import lombok.Data;

@Data
public class FileTypeStatVo {
    /**
     * 文件类型名称
     */
    private String name;

    /**
     * 平台占比 (百分比)
     */
    private Double platformRatio;

    /**
     * 文件空间 大写 MB 保留两位小数
     */
    private Double fileSpace;
}