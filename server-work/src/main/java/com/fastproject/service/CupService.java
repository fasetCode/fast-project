package com.fastproject.service;

import com.fastproject.domain.Cup;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CupService {

    /**
     * 保存 CPU 记录
     */
    Cup save(Cup cup);

    /**
     * 根据 ID 查询
     */
    Optional<Cup> findById(Long id);

    /**
     * 查询所有记录
     */
    List<Cup> findAll();

    /**
     * 根据时间范围查询
     */
    List<Cup> findByRecordTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 查询最新的 N 条记录
     */
    List<Cup> findLatest(int limit);

    /**
     * 删除记录
     */
    void deleteById(Long id);

    /**
     * 批量保存
     */
    List<Cup> saveAll(List<Cup> cups);
}
