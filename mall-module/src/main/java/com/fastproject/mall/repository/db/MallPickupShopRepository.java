package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallPickupShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 自提门店 Repository
 */
@Repository
public interface MallPickupShopRepository extends JpaRepository<MallPickupShop, Long>, JpaSpecificationExecutor<MallPickupShop> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}
