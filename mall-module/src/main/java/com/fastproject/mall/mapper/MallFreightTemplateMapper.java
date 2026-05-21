package com.fastproject.mall.mapper;

import com.fastproject.mall.domain.MallFreightTemplate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateCreate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateUpdate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MallFreightTemplateMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFreightTemplateFromDto(MallFreightTemplateUpdate dto, @MappingTarget MallFreightTemplate entity);

    MallFreightTemplate toFreightTemplate(MallFreightTemplateCreate create);

    List<MallFreightTemplateVo> toVo(List<MallFreightTemplate> content);

    MallFreightTemplateVo toVo(MallFreightTemplate entity);
}
