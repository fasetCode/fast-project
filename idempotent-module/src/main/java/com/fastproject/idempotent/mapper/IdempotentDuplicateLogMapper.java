package com.fastproject.idempotent.mapper;

import com.fastproject.idempotent.domain.IdempotentDuplicateLog;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogCreate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogUpdate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * 幂等重复提交记录 Mapper
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface IdempotentDuplicateLogMapper {

    /**
     * 更新操作 - 忽略 null 值
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLogFromDto(IdempotentDuplicateLogUpdate dto, @MappingTarget IdempotentDuplicateLog entity);

    /**
     * Create DTO -> Entity
     */
    IdempotentDuplicateLog toLog(IdempotentDuplicateLogCreate create);

    /**
     * Entity -> VO (单对象)
     */
    IdempotentDuplicateLogVo toVo(IdempotentDuplicateLog entity);

    /**
     * Entity -> VO (列表)
     */
    List<IdempotentDuplicateLogVo> toVo(List<IdempotentDuplicateLog> content);
}
