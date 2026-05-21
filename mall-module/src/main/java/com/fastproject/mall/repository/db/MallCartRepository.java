package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车 Repository
 */
@Repository
public interface MallCartRepository extends JpaRepository<MallCart, Long>, JpaSpecificationExecutor<MallCart> {

    List<MallCart> findByUserId(Long userId);

    List<MallCart> findByUserIdAndSelected(Long userId, Integer selected);

    MallCart findByUserIdAndProductIdAndSkuId(Long userId, Long productId, Long skuId);
}
