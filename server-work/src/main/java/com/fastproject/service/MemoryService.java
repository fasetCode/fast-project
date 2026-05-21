package com.fastproject.service;

import com.fastproject.domain.Memory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemoryService {

    /**
     * 保存内存记录
     */
    Memory save(Memory memory);

    /**
     * 根据 ID 查询
     */
    Optional<Memory> findById(Long id);

    /**
     * 查询所有记录
     */
    List<Memory> findAll();

    /**
     * 根据时间范围查询
     */
    List<Memory> findByRecordTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询最新的 N 条记录
     */
    List<Memory> findLatest(int limit);

    /**
     * 删除记录
     */
    void deleteById(Long id);

    /**
     * 批量保存
     */
    List<Memory> saveAll(List<Memory> memories);
}
