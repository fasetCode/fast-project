package com.fastproject.task;

import com.fastproject.collector.CpuCollector;
import com.fastproject.collector.CpuCollector.CpuUsageInfo;
import com.fastproject.collector.MemoryCollector;
import com.fastproject.collector.MemoryCollector.MemoryUsageInfo;
import com.fastproject.domain.Cup;
import com.fastproject.domain.Memory;
import com.fastproject.service.CupService;
import com.fastproject.service.MemoryService;
import com.fastproject.utils.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorCollectTask {

    private final CupService cupService;
    private final CpuCollector cpuCollector;
    private final MemoryService memoryService;
    private final MemoryCollector memoryCollector;

    /**
     * 每 5 秒采集一次 CPU 信息
     */
    @Scheduled(fixedRate = 5000)
    public void collectCpuInfo() {
        try {
            // 使用 CpuCollector 收集 CPU 信息
            CpuUsageInfo cpuInfo = cpuCollector.collect(5);

            // 保存记录
            Cup cup = new Cup();
            cup.setUsageRate(cpuInfo.usageRate());
            cup.setRecordTime(LocalDateTime.now());
            cup.setRecord(JsonUtils.toJson(cpuInfo.topProcesses()));

            cupService.save(cup);

            log.debug("CPU 监控数据采集完成，使用率: {}%", cpuInfo.usageRate());
        } catch (Exception e) {
            log.error("CPU 监控数据采集失败", e);
        }
    }

    /**
     * 每 10 秒采集一次内存信息
     */
    @Scheduled(fixedRate = 10000)
    public void collectMemoryInfo() {
        try {
            // 使用 MemoryCollector 收集内存信息
            MemoryUsageInfo memoryInfo = memoryCollector.collect(5);

            // 保存记录
            Memory memory = new Memory();
            memory.setUsageRate(memoryInfo.usageRate());
            memory.setTotalMemory(memoryInfo.totalMemory());
            memory.setUsedMemory(memoryInfo.usedMemory());
            memory.setAvailableMemory(memoryInfo.availableMemory());
            memory.setSwapUsageRate(memoryInfo.swapUsageRate());
            memory.setSwapTotal(memoryInfo.swapTotal());
            memory.setSwapUsed(memoryInfo.swapUsed());
            memory.setRecordTime(LocalDateTime.now());
            memory.setRecord(JsonUtils.toJson(memoryCollector.toMap(memoryInfo)));

            memoryService.save(memory);

            log.debug("内存监控数据采集完成，使用率: {}%", memoryInfo.usageRate());
        } catch (Exception e) {
            log.error("内存监控数据采集失败", e);
        }
    }
}
