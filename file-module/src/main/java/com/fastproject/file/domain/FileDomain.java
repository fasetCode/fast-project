package com.fastproject.file.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Table(name = "file_domain")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update file_domain set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class FileDomain extends BaseEntity {

    /**
     * 配置id
     */
    private Long configId;

    /**
     * 域名
     */
    private String domain;

    /**
     * 状态
     */
    private Integer status;

}
