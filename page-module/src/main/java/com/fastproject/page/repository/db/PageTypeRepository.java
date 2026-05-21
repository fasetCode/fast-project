package com.fastproject.page.repository.db;

import com.fastproject.page.domain.PageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PageTypeRepository extends JpaRepository<PageType, Long>, JpaSpecificationExecutor<PageType> {
    
    boolean existsByCode(String code);
    
    boolean existsByCodeAndIdNot(String code, Long id);
}
