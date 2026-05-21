package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallProductSku;
import com.fastproject.mall.vo.sku.MallProductSkuCreate;
import com.fastproject.mall.vo.sku.MallProductSkuUpdate;
import com.fastproject.mall.vo.sku.MallProductSkuVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallProductSkuMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkuFromDto(MallProductSkuUpdate dto, @MappingTarget MallProductSku entity);

    MallProductSku toSku(MallProductSkuCreate create);

    List<MallProductSkuVo> toVo(List<MallProductSku> content);

    MallProductSkuVo toVo(MallProductSku entity);
}
