package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallOrderItem;
import com.fastproject.mall.vo.orderitem.MallOrderItemCreate;
import com.fastproject.mall.vo.orderitem.MallOrderItemUpdate;
import com.fastproject.mall.vo.orderitem.MallOrderItemVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallOrderItemMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderItemFromDto(MallOrderItemUpdate dto, @MappingTarget MallOrderItem entity);

    MallOrderItem toOrderItem(MallOrderItemCreate create);

    List<MallOrderItemVo> toVo(List<MallOrderItem> content);

    MallOrderItemVo toVo(MallOrderItem entity);
}
