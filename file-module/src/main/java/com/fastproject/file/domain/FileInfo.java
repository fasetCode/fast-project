package com.fastproject.file.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "file_info")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update file_info set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class FileInfo extends BaseEntity {

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
    @Column(length = 200)
    private String fileStorage;

    /**
     * 访问路径
     */
    @Column(length = 2000)
    private String accessPath;

    /**
     * 文件路径（如 /2026/04/01/64788981cb124e798613047ab4e34aa9.jpg）
     */
    @Column(length = 2000)
    private String filePath;

    /**
     * 配置id
     */
    private Long configId;

    /**
     * 文件类型id
     */
    private Long fileTypeId;

//    /**
//     * 路径 如 /2026/04/01/64788981cb124e798613047ab4e34aa9.jpg 这样的
//     */
//    private String filePath;

}
