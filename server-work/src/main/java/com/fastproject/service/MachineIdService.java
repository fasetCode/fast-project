package com.fastproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 机器唯一标识服务
 * 基于 CPU + 主板 + 磁盘 + OS GUID 生成 SHA256 唯一标识
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MachineIdService {

    private final SystemInfo systemInfo;


    /**
     * 获取机器唯一标识（所有字段已 MD5 加密）n     *
     * @return 机器唯一标识信息
     */
    public String getMachineId() {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        OperatingSystem os = systemInfo.getOperatingSystem();
        ComputerSystem computerSystem = hal.getComputerSystem();

        // 获取 CPU ID 并 MD5 加密
        String cpuId = generateMd5(getCpuId(hal));

        // 获取主板序列号并 MD5 加密
        String motherboardSerial = generateMd5(getMotherboardSerial(computerSystem));

        // 获取磁盘序列号并 MD5 加密
        String diskSerial = generateMd5(getDiskSerial(hal));

        // 获取 OS GUID 并 MD5 加密
        String osGuid = generateMd5(getOsGuid(os));

        // 组合 MD5 加密后的字段生成 SHA256 机器唯一标识
        String combined = cpuId + motherboardSerial + diskSerial + osGuid;
        String machineId = generateSha256(combined);

        log.debug("Machine ID generated");

        return machineId;
    }

    /**
     * 获取 CPU ID
     */
    private String getCpuId(HardwareAbstractionLayer hal) {
        try {
            CentralProcessor processor = hal.getProcessor();
            return processor.getProcessorIdentifier().getProcessorID();
        } catch (Exception e) {
            log.warn("Failed to get CPU ID", e);
            return "UNKNOWN_CPU";
        }
    }

    /**
     * 获取主板序列号
     */
    private String getMotherboardSerial(ComputerSystem computerSystem) {
        try {
            return computerSystem.getSerialNumber();
        } catch (Exception e) {
            log.warn("Failed to get motherboard serial", e);
            return "UNKNOWN_MB";
        }
    }

    /**
     * 获取磁盘序列号（取第一个非空磁盘）
     */
    private String getDiskSerial(HardwareAbstractionLayer hal) {
        try {
            List<HWDiskStore> diskStores = hal.getDiskStores();
            return diskStores.stream()
                    .filter(disk -> disk.getSerial() != null && !disk.getSerial().trim().isEmpty())
                    .findFirst()
                    .map(HWDiskStore::getSerial)
                    .orElse("UNKNOWN_DISK");
        } catch (Exception e) {
            log.warn("Failed to get disk serial", e);
            return "UNKNOWN_DISK";
        }
    }

    /**
     * 获取 OS GUID
     */
    private String getOsGuid(OperatingSystem os) {
        try {
            // 尝试获取 OS 的唯一标识
            String osManufacturer = os.getManufacturer();
            String family = os.getFamily();
            String version = os.getVersionInfo().getVersion();
            String build = os.getVersionInfo().getBuildNumber();
            
            // 组合 OS 信息作为唯一标识
            return String.format("%s|%s|%s|%s", osManufacturer, family, version, build);
        } catch (Exception e) {
            log.warn("Failed to get OS GUID", e);
            return "UNKNOWN_OS";
        }
    }

    /**
     * 生成 SHA256 哈希
     */
    private String generateSha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-256 algorithm not available", e);
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * 生成 MD5 哈希
     */
    private String generateMd5(String input) {
        if (input == null || input.isEmpty()) {
            return generateMd5("EMPTY");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 algorithm not available", e);
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
}
