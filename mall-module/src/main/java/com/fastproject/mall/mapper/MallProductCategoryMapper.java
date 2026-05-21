package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallProductCategory;
import com.fastproject.mall.vo.category.MallProductCategoryCreate;
import com.fastproject.mall.vo.category.MallProductCategoryUpdate;
import com.fastproject.mall.vo.category.MallProductCategoryVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallProductCategoryMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDto(MallProductCategoryUpdate dto, @MappingTarget MallProductCategory entity);

    MallProductCategory toCategory(MallProductCategoryCreate create);

    List<MallProductCategoryVo> toVo(List<MallProductCategory> content);

    MallProductCategoryVo toVo(MallProductCategory entity);
}
