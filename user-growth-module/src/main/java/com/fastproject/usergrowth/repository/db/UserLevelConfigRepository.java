package com.fastproject.usergrowth.repository.db;

import com.fastproject.usergrowth.domain.UserLevelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLevelConfigRepository extends JpaRepository<UserLevelConfig, Long>, JpaSpecificationExecutor<UserLevelConfig> {

    List<UserLevelConfig> findByStatusOrderByGrowthValueAsc(Integer status);
}
