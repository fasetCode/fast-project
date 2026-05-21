package com.fastproject.vo;

import lombok.Data;

import java.util.List;

@Data
public class SystemVo {

    /**
     * 系统信息
     */
    private SysInfo sysInfo;

    /**
     * CPU 信息
     */
    private CpuInfo cpuInfo;

    /**
     * 内存信息
     */
    private MemInfo memInfo;

    /**
     * 磁盘信息列表
     */
    private List<DiskInfo> diskInfos;

    /**
     * 系统运行时长（秒）
     */
    private long uptime;

    @Data
    public static class SysInfo {
        /** 操作系统名称 */
        private String osName;
        /** 操作系统版本 */
        private String osVersion;
        /** 系统架构 */
        private String arch;
        /** 主机名 */
        private String computerName;
        /** 本地 IP */
        private String ip;
    }

    @Data
    public static class CpuInfo {
        /** CPU 型号 */
        private String cpuModel;
        /** 物理 CPU 数 */
        private int physicalPackageCount;
        /** 物理核心数 */
        private int physicalProcessorCount;
        /** 逻辑核心数 */
        private int logicalProcessorCount;
        /** CPU 使用率（百分比） */
        private double usageRate;
    }

    @Data
    public static class MemInfo {
        /** 总内存（字节） */
        private long total;
        /** 已用内存（字节） */
        private long used;
        /** 空闲内存（字节） */
        private long free;
        /** 使用率（百分比） */
        private double usageRate;
    }

    @Data
    public static class DiskInfo {
        /** 盘符路径 */
        private String mountPoint;
        /** 文件系统类型 */
        private String fsType;
        /** 总空间（字节） */
        private long total;
        /** 已用空间（字节） */
        private long used;
        /** 可用空间（字节） */
        private long free;
        /** 使用率（百分比） */
        private double usageRate;
    }

}