package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysDictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 字典类型 Repository
 */
@Repository
public interface SysDictTypeRepository extends JpaRepository<SysDictType, Long>, JpaSpecificationExecutor<SysDictType> {

    /**
     * 判断字典类型是否存在
     */
    boolean existsByType(String type);

    /**
     * 判断字典类型是否存在（排除指定ID）
     */
    boolean existsByTypeAndIdNot(String type, Long id);
}
