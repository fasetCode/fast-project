package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentCategory;
import com.fastproject.content.vo.category.ContentCategoryCreate;
import com.fastproject.content.vo.category.ContentCategoryUpdate;
import com.fastproject.content.vo.category.ContentCategoryVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentCategoryMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentCategoryUpdate dto, @MappingTarget ContentCategory entity);

    ContentCategory toEntity(ContentCategoryCreate create);

    ContentCategoryVo toVo(ContentCategory entity);

    List<ContentCategoryVo> toVo(List<ContentCategory> list);
}
