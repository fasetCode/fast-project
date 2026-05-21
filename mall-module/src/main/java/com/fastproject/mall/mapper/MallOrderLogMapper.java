package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallOrderLog;
import com.fastproject.mall.vo.orderlog.MallOrderLogCreate;
import com.fastproject.mall.vo.orderlog.MallOrderLogUpdate;
import com.fastproject.mall.vo.orderlog.MallOrderLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallOrderLogMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderLogFromDto(MallOrderLogUpdate dto, @MappingTarget MallOrderLog entity);

    MallOrderLog toOrderLog(MallOrderLogCreate create);

    List<MallOrderLogVo> toVo(List<MallOrderLog> content);

    MallOrderLogVo toVo(MallOrderLog entity);
}
