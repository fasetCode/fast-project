package com.fastproject.idempotent.repository.db;

import com.fastproject.idempotent.domain.IdempotentDuplicateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 幂等重复提交记录 Repository
 */
@Repository
public interface IdempotentDuplicateLogRepository extends JpaRepository<IdempotentDuplicateLog, Long>, JpaSpecificationExecutor<IdempotentDuplicateLog> {

    /**
     * 根据请求ID查询
     */
    Optional<IdempotentDuplicateLog> findByRequestId(String requestId);

    /**
     * 判断请求ID是否存在
     */
    boolean existsByRequestId(String requestId);
}
