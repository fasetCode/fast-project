package com.fastproject.file.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "file_type")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update file_type set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class FileType extends BaseEntity {
    /**
     * 文件类型名称
     */
    private String name;

    /**
     * 平台占比
     */
    private Double platformRatio;

    /**
     * 文件空间 大写 MB 保留两位小数
     */
    private Double fileSpace;



}
