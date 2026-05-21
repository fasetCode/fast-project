package com.fastproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "memory")
@Getter
@Setter
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 内存占用率 (%)
     */
    private Double usageRate;

    /**
     * 总内存 (bytes)
     */
    private Long totalMemory;

    /**
     * 已用内存 (bytes)
     */
    private Long usedMemory;

    /**
     * 可用内存 (bytes)
     */
    private Long availableMemory;

    /**
     * Swap 使用率 (%)
     */
    private Double swapUsageRate;

    /**
     * 总 Swap (bytes)
     */
    private Long swapTotal;

    /**
     * 已用 Swap (bytes)
     */
    private Long swapUsed;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 详细记录 (JSON 格式)
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String record;

}
