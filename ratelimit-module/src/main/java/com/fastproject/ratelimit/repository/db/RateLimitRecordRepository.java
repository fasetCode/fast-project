package com.fastproject.ratelimit.repository.db;

import com.fastproject.ratelimit.domain.RateLimitRecord;
import com.fastproject.ratelimit.enums.RateLimitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 限流记录 Repository
 */
@Repository
public interface RateLimitRecordRepository extends JpaRepository<RateLimitRecord, Long>, JpaSpecificationExecutor<RateLimitRecord> {

    /**
     * 根据限流标识Key查询记录
     */
    List<RateLimitRecord> findByLimitKey(String limitKey);

    /**
     * 根据限流类型查询记录
     */
    List<RateLimitRecord> findByLimitType(RateLimitType limitType);

    /**
     * 根据目标值查询记录
     */
    List<RateLimitRecord> findByTargetValue(String targetValue);

    /**
     * 根据限流标识Key和限流类型查询记录
     */
    List<RateLimitRecord> findByLimitKeyAndLimitType(String limitKey, RateLimitType limitType);

    /**
     * 根据用户ID查询记录
     */
    List<RateLimitRecord> findByUserId(Long userId);

    /**
     * 根据IP查询记录
     */
    List<RateLimitRecord> findByIp(String ip);
}