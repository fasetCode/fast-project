package com.fastproject.file.repository.db;

import com.fastproject.file.domain.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 文件类型 Repository
 */
@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Long>, JpaSpecificationExecutor<FileType> {

    /**
     * 根据名称查询
     */
    FileType findByName(String name);
}
