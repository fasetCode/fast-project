package com.fastproject.collector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 内存信息收集器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemoryCollector {

    private final SystemInfo systemInfo;

    /**
     * 内存使用率信息
     */
    public record MemoryUsageInfo(
            long totalMemory,
            long availableMemory,
            long usedMemory,
            double usageRate,
            long swapTotal,
            long swapUsed,
            double swapUsageRate,
            List<Map<String, Object>> topProcesses
    ) {
    }

    /**
     * 收集内存信息
     *
     * @param topProcessLimit 获取内存占用前 N 的进程
     * @return 内存使用率信息
     */
    public MemoryUsageInfo collect(int topProcessLimit) {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        OperatingSystem os = systemInfo.getOperatingSystem();
        GlobalMemory globalMemory = hal.getMemory();
        VirtualMemory virtualMemory = globalMemory.getVirtualMemory();

        // 物理内存信息
        long totalMemory = globalMemory.getTotal();
        long availableMemory = globalMemory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        double usageRate = totalMemory > 0 ? (double) usedMemory / totalMemory * 100 : 0;

        // 交换内存信息
        long swapTotal = virtualMemory.getSwapTotal();
        long swapUsed = virtualMemory.getSwapUsed();
        double swapUsageRate = swapTotal > 0 ? (double) swapUsed / swapTotal * 100 : 0;

        // 获取内存占用前 N 的进程
        List<Map<String, Object>> topProcesses = getTopProcesses(os, globalMemory, topProcessLimit);

        return new MemoryUsageInfo(
                totalMemory,
                availableMemory,
                usedMemory,
                Math.round(usageRate * 100.0) / 100.0,
                swapTotal,
                swapUsed,
                Math.round(swapUsageRate * 100.0) / 100.0,
                topProcesses
        );
    }

    /**
     * 获取内存占用前 N 的进程
     */
    private List<Map<String, Object>> getTopProcesses(OperatingSystem os, GlobalMemory globalMemory, int limit) {
        List<OSProcess> processes = os.getProcesses();

        return processes.stream()
                .sorted(Comparator.comparingLong(OSProcess::getResidentSetSize).reversed())
                .limit(limit)
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pid", p.getProcessID());
                    map.put("name", p.getName());
                    map.put("memoryUsage", bytesToMB(p.getResidentSetSize()));
                    map.put("memoryUsagePercent", calculateMemoryPercent(p.getResidentSetSize(), globalMemory.getTotal()));
                    map.put("user", p.getUser());
                    map.put("commandLine", p.getCommandLine());
                    return map;
                })
                .collect(Collectors.toList());
    }

    /**
     * 计算内存使用百分比
     */
    private double calculateMemoryPercent(long used, long total) {
        if (total <= 0) {
            return 0.0;
        }
        return Math.round((double) used / total * 10000.0) / 100.0;
    }

    /**
     * 将内存信息转换为 Map，便于 JSON 序列化
     */
    public Map<String, Object> toMap(MemoryUsageInfo info) {
        Map<String, Object> map = new HashMap<>();
        map.put("totalMemory", info.totalMemory());
        map.put("totalMemoryGB", bytesToGB(info.totalMemory()));
        map.put("availableMemory", info.availableMemory());
        map.put("availableMemoryGB", bytesToGB(info.availableMemory()));
        map.put("usedMemory", info.usedMemory());
        map.put("usedMemoryGB", bytesToGB(info.usedMemory()));
        map.put("usageRate", info.usageRate());
        map.put("swapTotal", info.swapTotal());
        map.put("swapTotalGB", bytesToGB(info.swapTotal()));
        map.put("swapUsed", info.swapUsed());
        map.put("swapUsedGB", bytesToGB(info.swapUsed()));
        map.put("swapUsageRate", info.swapUsageRate());
        map.put("topProcesses", info.topProcesses());
        return map;
    }

    /**
     * 字节转换为 GB
     */
    private double bytesToGB(long bytes) {
        return Math.round(bytes / (1024.0 * 1024.0 * 1024.0) * 100.0) / 100.0;
    }

    /**
     * 字节转换为 MB
     */
    private double bytesToMB(long bytes) {
        return Math.round(bytes / (1024.0 * 1024.0) * 100.0) / 100.0;
    }
}