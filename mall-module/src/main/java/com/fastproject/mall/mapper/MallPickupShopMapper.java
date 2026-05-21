package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallPickupShop;
import com.fastproject.mall.vo.pickupshop.MallPickupShopCreate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopUpdate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallPickupShopMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePickupShopFromDto(MallPickupShopUpdate dto, @MappingTarget MallPickupShop entity);

    MallPickupShop toPickupShop(MallPickupShopCreate create);

    List<MallPickupShopVo> toVo(List<MallPickupShop> content);

    MallPickupShopVo toVo(MallPickupShop entity);
}
