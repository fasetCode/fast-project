package com.fastproject.system.domain;

import com.fastproject.db.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

/**
 * 字典类型
 */
@Table(name = "sys_dict_type")
@Getter
@Setter
@Entity
@SQLDelete(sql = "update sys_dict_type set deleted = 1 where id = ?")
@SQLRestriction("deleted = 0")
public class SysDictType extends BaseEntity {

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
