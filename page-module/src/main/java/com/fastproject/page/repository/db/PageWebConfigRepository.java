package com.fastproject.page.repository.db;

import com.fastproject.page.domain.PageWebConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PageWebConfigRepository extends JpaRepository<PageWebConfig, Long>, JpaSpecificationExecutor<PageWebConfig> {

    PageWebConfig findByPathUrlAndApplication_Id(String pathUrl, Long applicationId);


    PageWebConfig findByPathUrlAndApplication_Code(String pathUrl,String code);
}
