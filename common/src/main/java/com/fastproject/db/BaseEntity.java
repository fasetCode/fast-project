package com.fastproject.db;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;

@Data
@EntityListeners(SnowflakeIdListener.class)
@MappedSuperclass
public abstract class BaseEntity {

    /**
     *  主键
     */
    @Id
    private Long id;

    /**
     *  创建人
     */
    private Long createBy;

    /**
     *  创建时间
     */
    private LocalDateTime createTime;

    /**
     *  更新人
     */
    private Long updateBy;

    /**
     *  更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;

}
