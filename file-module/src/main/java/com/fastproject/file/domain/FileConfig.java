package com.fastproject.file.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "file_config")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update file_config set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class FileConfig extends BaseEntity {

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 访问域名
     */
    private String accessDomain;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 类型 本地 / 远程
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 远程url地址
     */
    private String remoteUrl;

    /**
     * 远程上传凭证
     */
    private String remoteToken;

    /**
     * 权重（用于多配置选择，越大优先级越高）
     */
    private Integer weight;

    /**
     * 配置
     */
    @Column(columnDefinition = "TEXT")
    private String config;
}
