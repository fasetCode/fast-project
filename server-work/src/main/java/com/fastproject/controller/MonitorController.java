package com.fastproject.controller;

import com.fastproject.service.MachineIdService;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统监控 Controller
 * 提供机器信息、性能指标等接口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monitor")
public class MonitorController {

    private final MachineIdService machineIdService;

    /**
     * 获取机器唯一标识
     * 基于 CPU + 主板 + 磁盘 + OS GUID 生成 SHA256 哈希（各字段已 MD5 加密）
     * ea85d3e555896fe5a264908dbdf51171890258b18dc1457f2600fd895b3db0b5
     * @return 机器唯一标识信息
     */
    @GetMapping("/machine-id")
    public ResultVo<String> getMachineId() {
        String machineIdInfo = machineIdService.getMachineId();
        return ResultVo.success(machineIdInfo);
    }
}
