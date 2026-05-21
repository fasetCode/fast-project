package com.fastproject.page.repository.db;

import com.fastproject.page.domain.PageComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageComponentRepository extends JpaRepository<PageComponent, Long>, JpaSpecificationExecutor<PageComponent> {
    
    boolean existsByCode(String code);
    
    boolean existsByCodeAndIdNot(String code, Long id);

    List<PageComponent> findByType_Id(Long id);
}
