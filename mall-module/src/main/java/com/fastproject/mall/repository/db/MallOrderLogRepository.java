package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 订单日志 Repository
 */
@Repository
public interface MallOrderLogRepository extends JpaRepository<MallOrderLog, Long>, JpaSpecificationExecutor<MallOrderLog> {
}
