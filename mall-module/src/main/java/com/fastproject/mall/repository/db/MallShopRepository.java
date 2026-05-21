package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 商城店铺 Repository
 */
@Repository
public interface MallShopRepository extends JpaRepository<MallShop, Long>, JpaSpecificationExecutor<MallShop> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}
