package com.fastproject.repository;

import com.fastproject.domain.Cup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CupRepository extends JpaRepository<Cup, Long>, JpaSpecificationExecutor<Cup> {

    /**
     * 根据时间范围查询 CPU 记录
     */
    List<Cup> findByRecordTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询最新的 N 条记录
     */
    List<Cup> findTopNByOrderByRecordTimeDesc(int limit);
}
