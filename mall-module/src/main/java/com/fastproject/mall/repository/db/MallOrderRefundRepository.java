package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallOrderRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 退款单 Repository
 */
@Repository
public interface MallOrderRefundRepository extends JpaRepository<MallOrderRefund, Long>, JpaSpecificationExecutor<MallOrderRefund> {

    MallOrderRefund findByRefundNo(String refundNo);
}
