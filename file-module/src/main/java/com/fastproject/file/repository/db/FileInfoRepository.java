package com.fastproject.file.repository.db;

import com.fastproject.file.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 文件信息 Repository
 */
@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long>, JpaSpecificationExecutor<FileInfo> {

    /**
     * 根据文件MD5查询
     */
    Optional<FileInfo> findByFileMd5(String fileMd5);

    /**
     * 判断文件MD5是否存在
     */
    boolean existsByFileMd5(String fileMd5);

    /**
     * 根据访问路径查询
     */
    Optional<FileInfo> findByAccessPath(String accessPath);
}
