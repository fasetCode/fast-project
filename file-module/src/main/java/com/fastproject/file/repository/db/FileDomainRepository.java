package com.fastproject.file.repository.db;

import com.fastproject.file.domain.FileDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件域名 Repository
 */
@Repository
public interface FileDomainRepository extends JpaRepository<FileDomain, Long>, JpaSpecificationExecutor<FileDomain> {

    /**
     * 根据域名查询
     */
    FileDomain findByDomain(String domain);

    /**
     * 判断域名是否存在
     */
    boolean existsByDomain(String domain);

    /**
     * 判断域名是否存在（排除指定ID）
     */
    boolean existsByDomainAndIdNot(String domain, Long id);

    /**
     * 根据配置ID查询域名列表
     */
    List<FileDomain> findByConfigId(Long configId);

    /**
     * 查询所有启用的域名
     */
    List<FileDomain> findByStatusOrderByIdDesc(Integer status);

    List<FileDomain> findByConfigIdAndStatus(Long configId, Integer status);
}
