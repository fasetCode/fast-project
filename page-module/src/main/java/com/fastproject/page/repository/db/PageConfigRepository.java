package com.fastproject.page.repository.db;

import com.fastproject.page.domain.PageConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageConfigRepository extends JpaRepository<PageConfig, Long>, JpaSpecificationExecutor<PageConfig> {

    List<PageConfig> findByApplicationId(Long applicationId);

}
