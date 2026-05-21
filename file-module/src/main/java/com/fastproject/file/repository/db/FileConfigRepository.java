package com.fastproject.file.repository.db;

import com.fastproject.file.domain.FileConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件配置 Repository
 */
@Repository
public interface FileConfigRepository extends JpaRepository<FileConfig, Long>, JpaSpecificationExecutor<FileConfig> {

    /**
     * 根据类型查询配置
     */
    FileConfig findByType(String type);

    /**
     * 判断类型是否存在
     */
    boolean existsByType(String type);

    /**
     * 判断类型是否存在（排除指定ID）
     */
    boolean existsByTypeAndIdNot(String type, Long id);

    /**
     * 查询所有启用的配置
     */
    List<FileConfig> findByStatusOrderByWeightDesc(Integer status);
}
