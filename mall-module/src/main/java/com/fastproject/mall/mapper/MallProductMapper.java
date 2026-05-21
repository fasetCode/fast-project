package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallProduct;
import com.fastproject.mall.vo.product.MallProductCreate;
import com.fastproject.mall.vo.product.MallProductUpdate;
import com.fastproject.mall.vo.product.MallProductVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallProductMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(MallProductUpdate dto, @MappingTarget MallProduct entity);

    MallProduct toProduct(MallProductCreate create);

    List<MallProductVo> toVo(List<MallProduct> content);

    @Mapping(target = "shopName", source = "shop.name")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "shop.id", source = "shop.id")
    @Mapping(target = "shop.name", source = "shop.name")
    @Mapping(target = "category.id", source = "category.id")
    @Mapping(target = "category.name", source = "category.name")
    @Mapping(target = "brand.id", source = "brand.id")
    @Mapping(target = "brand.name", source = "brand.name")
    MallProductVo toVo(MallProduct entity);
}
