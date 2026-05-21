package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品SKU Repository
 */
@Repository
public interface MallProductSkuRepository extends JpaRepository<MallProductSku, Long>, JpaSpecificationExecutor<MallProductSku> {

    boolean existsBySkuSn(String skuSn);

    boolean existsBySkuSnAndIdNot(String skuSn, Long id);

    List<MallProductSku> findByProductId(Long productId);

    List<MallProductSku> findByProductIdAndStatus(Long productId, Integer status);

    List<MallProductSku> findByShopId(Long shopId);
}
