package com.fastproject.mall.repository.db;

import com.fastproject.mall.domain.MallFreightTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 运费模板 Repository
 */
@Repository
public interface MallFreightTemplateRepository extends JpaRepository<MallFreightTemplate, Long>, JpaSpecificationExecutor<MallFreightTemplate> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    MallFreightTemplate findByShopIdAndIsDefault(Long shopId, Integer isDefault);
}
