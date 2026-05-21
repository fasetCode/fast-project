package com.fastproject.logs.mapper;

import com.fastproject.logs.domain.OperationLog;
import com.fastproject.logs.vo.OperationLogCreate;
import com.fastproject.logs.vo.OperationLogUpdate;
import com.fastproject.logs.vo.OperationLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface OperationLogMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOperationLogFromDto(OperationLogUpdate dto, @MappingTarget OperationLog entity);

    OperationLog toOperationLog(OperationLogCreate create);

    List<OperationLogVo> toVo(List<OperationLog> content);

    OperationLogVo toVo(OperationLog entity);
}
