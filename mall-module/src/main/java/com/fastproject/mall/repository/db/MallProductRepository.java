package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * 商品 Repository
 */
@Repository
public interface MallProductRepository extends JpaRepository<MallProduct, Long>, JpaSpecificationExecutor<MallProduct> {

    @Override
    @EntityGraph(attributePaths = {"shop", "category", "brand"})
    Optional<MallProduct> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"shop", "category", "brand"})
    Page<MallProduct> findAll(Specification<MallProduct> spec, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"shop", "category", "brand"})
    List<MallProduct> findAll(Sort sort);

    @Override
    @EntityGraph(attributePaths = {"shop", "category", "brand"})
    List<MallProduct> findAll(Specification<MallProduct> spec, Sort sort);

    boolean existsByProductSn(String productSn);

    boolean existsByProductSnAndIdNot(String productSn, Long id);

    List<MallProduct> findByCategoryId(Long categoryId);

    List<MallProduct> findByBrandId(Long brandId);

    List<MallProduct> findByShopId(Long shopId);
}
