package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 订单 Repository
 */
@Repository
public interface MallOrderRepository extends JpaRepository<MallOrder, Long>, JpaSpecificationExecutor<MallOrder> {

    MallOrder findByOrderNo(String orderNo);
}
