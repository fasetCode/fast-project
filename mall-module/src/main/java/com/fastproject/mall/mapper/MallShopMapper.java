package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallShop;
import com.fastproject.mall.vo.shop.MallShopCreate;
import com.fastproject.mall.vo.shop.MallShopUpdate;
import com.fastproject.mall.vo.shop.MallShopVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallShopMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateShopFromDto(MallShopUpdate dto, @MappingTarget MallShop entity);

    MallShop toShop(MallShopCreate create);

    List<MallShopVo> toVo(List<MallShop> content);

    MallShopVo toVo(MallShop entity);
}
