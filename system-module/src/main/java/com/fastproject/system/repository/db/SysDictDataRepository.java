package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SysDictData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典数据 Repository
 */
@Repository
public interface SysDictDataRepository extends JpaRepository<SysDictData, Long>, JpaSpecificationExecutor<SysDictData> {

    /**
     * 根据字典类型ID查询字典数据
     */
    List<SysDictData> findByDictTypeId(Long dictTypeId);

    /**
     * 判断字典值是否存在
     */
    boolean existsByValueAndDictTypeId(String value, Long dictTypeId);

    /**
     * 判断字典值是否存在（排除指定ID）
     */
    boolean existsByValueAndDictTypeIdAndIdNot(String value, Long dictTypeId, Long id);
}
