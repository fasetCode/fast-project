package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 字典数据
 */
@Table(name = "sys_dict_data")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_dict_data set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysDictData extends BaseEntity {

    /**
     * 字典排序
     */
    private Integer sort;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典类型ID
     */
    private Long dictTypeId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
