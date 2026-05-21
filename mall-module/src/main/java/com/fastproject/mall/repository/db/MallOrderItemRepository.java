package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单明细 Repository
 */
@Repository
public interface MallOrderItemRepository extends JpaRepository<MallOrderItem, Long>, JpaSpecificationExecutor<MallOrderItem> {

    List<MallOrderItem> findByOrderId(Long orderId);

    List<MallOrderItem> findByOrderNo(String orderNo);
}
