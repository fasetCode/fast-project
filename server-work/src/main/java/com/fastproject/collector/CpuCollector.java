package com.fastproject.collector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CPU 信息收集器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CpuCollector {

    private final SystemInfo systemInfo;

    /**
     * CPU 使用率信息
     */
    public record CpuUsageInfo(double usageRate, List<Map<String, Object>> topProcesses) {
    }

    /**
     * 收集 CPU 信息
     *
     * @param topProcessLimit 获取 CPU 占用前 N 的进程
     * @return CPU 使用率信息
     */
    public CpuUsageInfo collect(int topProcessLimit) {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        OperatingSystem os = systemInfo.getOperatingSystem();
        CentralProcessor processor = hal.getProcessor();

        // 计算 CPU 使用率
        double usageRate = calculateCpuUsage(processor);

        // 获取 CPU 占用前 N 的进程
        List<Map<String, Object>> topProcesses = getTopProcesses(os, topProcessLimit);

        return new CpuUsageInfo(usageRate, topProcesses);
    }

    /**
     * 计算 CPU 使用率
     */
    private double calculateCpuUsage(CentralProcessor processor) {
        long[] oldTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(500);
        long[] newTicks = processor.getSystemCpuLoadTicks();

        long user = newTicks[CentralProcessor.TickType.USER.getIndex()]
                - oldTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = newTicks[CentralProcessor.TickType.NICE.getIndex()]
                - oldTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = newTicks[CentralProcessor.TickType.SYSTEM.getIndex()]
                - oldTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = newTicks[CentralProcessor.TickType.IDLE.getIndex()]
                - oldTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = newTicks[CentralProcessor.TickType.IOWAIT.getIndex()]
                - oldTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = newTicks[CentralProcessor.TickType.IRQ.getIndex()]
                - oldTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = newTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()]
                - oldTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = newTicks[CentralProcessor.TickType.STEAL.getIndex()]
                - oldTicks[CentralProcessor.TickType.STEAL.getIndex()];

        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
        double usage = totalCpu > 0 ? (1.0 - (double) idle / totalCpu) * 100 : 0;

        return Math.round(usage * 100.0) / 100.0;
    }

    /**
     * 获取 CPU 占用前 N 的进程
     */
    private List<Map<String, Object>> getTopProcesses(OperatingSystem os, int limit) {
        List<OSProcess> processes = os.getProcesses();

        return processes.stream()
                .sorted(Comparator.comparingDouble(OSProcess::getProcessCpuLoadCumulative).reversed())
                .limit(limit)
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("pid", p.getProcessID());
                    map.put("name", p.getName());
                    map.put("cpuUsage", Math.round(p.getProcessCpuLoadCumulative() * 10000.0) / 100.0);
                    map.put("user", p.getUser());
                    map.put("commandLine", p.getCommandLine());
                    return map;
                })
                .collect(Collectors.toList());
    }
}
