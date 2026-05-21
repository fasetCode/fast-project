package com.fastproject.page.repository.db;

import com.fastproject.page.domain.PageApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PageApplicationRepository extends JpaRepository<PageApplication, Long>, JpaSpecificationExecutor<PageApplication> {
    
    boolean existsByCode(String code);
    
    boolean existsByCodeAndIdNot(String code, Long id);
}
