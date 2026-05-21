package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 商品品牌 Repository
 */
@Repository
public interface MallProductBrandRepository extends JpaRepository<MallProductBrand, Long>, JpaSpecificationExecutor<MallProductBrand> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Long id);
}
