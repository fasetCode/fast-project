package com.fastproject.system.repository.db;

import com.fastproject.system.domain.SlowQueryLog;
import com.fastproject.system.enums.SlowQuerySeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SlowQueryLogRepository extends JpaRepository<SlowQueryLog, Long>, JpaSpecificationExecutor<SlowQueryLog> {
    
    /**
     * 根据 MD5 查找记录
     */
    Optional<SlowQueryLog> findBySqlMd5(String sqlMd5);

    /**
     * 根据 MD5 和严重程度查找记录
     */
    Optional<SlowQueryLog> findBySqlMd5AndSeverity(String sqlMd5, SlowQuerySeverity severity);
}
