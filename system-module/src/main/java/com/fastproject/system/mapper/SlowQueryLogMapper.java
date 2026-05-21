package com.fastproject.system.mapper;

import com.fastproject.system.domain.SlowQueryLog;
import com.fastproject.system.vo.slowquery.SlowQueryLogVo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlowQueryLogMapper {
    
    public SlowQueryLogVo toVo(SlowQueryLog entity) {
        if (entity == null) return null;
        SlowQueryLogVo vo = new SlowQueryLogVo();
        vo.setId(entity.getId());
        vo.setSqlContent(entity.getSqlContent());
        vo.setSqlMd5(entity.getSqlMd5());
        vo.setExecutionTime(entity.getExecutionTime());
        if (entity.getSeverity() != null) {
            vo.setSeverity(entity.getSeverity().getDescription());
        }
        vo.setModule(entity.getModule());
        vo.setTriggerCount(entity.getTriggerCount());
        if (entity.getStatus() != null) {
            vo.setStatus(entity.getStatus().getDescription());
        }
        vo.setRemark(entity.getRemark());
        vo.setCreateTime(entity.getCreateTime());
        return vo;
    }

    public List<SlowQueryLogVo> toVo(List<SlowQueryLog> list) {
        if (list == null) return null;
        return list.stream().map(this::toVo).collect(Collectors.toList());
    }
}
