package com.fastproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cpu")
@Getter
@Setter
public class Cup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * cpu占用率
     */
    private Double usageRate;

    /**
     * 记录时间
     */
    private LocalDateTime recordTime;

    /**
     * 记录前 5 的 进程 PID 进程名 cup占用 启动用户名 cmd 用 json
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String record;

}
