package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallProductBrand;
import com.fastproject.mall.vo.brand.MallProductBrandCreate;
import com.fastproject.mall.vo.brand.MallProductBrandUpdate;
import com.fastproject.mall.vo.brand.MallProductBrandVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallProductBrandMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrandFromDto(MallProductBrandUpdate dto, @MappingTarget MallProductBrand entity);

    MallProductBrand toBrand(MallProductBrandCreate create);

    List<MallProductBrandVo> toVo(List<MallProductBrand> content);

    MallProductBrandVo toVo(MallProductBrand entity);
}
