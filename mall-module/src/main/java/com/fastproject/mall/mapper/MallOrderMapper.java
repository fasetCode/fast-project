package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallOrder;
import com.fastproject.mall.vo.order.MallOrderCreate;
import com.fastproject.mall.vo.order.MallOrderUpdate;
import com.fastproject.mall.vo.order.MallOrderVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallOrderMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(MallOrderUpdate dto, @MappingTarget MallOrder entity);

    MallOrder toOrder(MallOrderCreate create);

    List<MallOrderVo> toVo(List<MallOrder> content);

    MallOrderVo toVo(MallOrder entity);
}
