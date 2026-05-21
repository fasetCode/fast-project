package com.fastproject.repository;

import com.fastproject.domain.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Long>, JpaSpecificationExecutor<Memory> {

    /**
     * 根据时间范围查询内存记录
     */
    List<Memory> findByRecordTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询最新的 N 条记录
     */
    List<Memory> findTopNByOrderByRecordTimeDesc(int limit);
}
