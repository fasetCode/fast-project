package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品分类 Repository
 */
@Repository
public interface MallProductCategoryRepository extends JpaRepository<MallProductCategory, Long>, JpaSpecificationExecutor<MallProductCategory> {

    List<MallProductCategory> findByParentId(Long parentId);

    List<MallProductCategory> findByParentIdAndStatus(Long parentId, Integer status);
}
