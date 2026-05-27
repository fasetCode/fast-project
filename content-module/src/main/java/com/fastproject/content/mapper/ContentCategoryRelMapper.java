package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentCategoryRel;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelCreate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelUpdate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentCategoryRelMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentCategoryRelUpdate dto, @MappingTarget ContentCategoryRel entity);

    ContentCategoryRel toEntity(ContentCategoryRelCreate create);

    ContentCategoryRelVo toVo(ContentCategoryRel entity);

    List<ContentCategoryRelVo> toVo(List<ContentCategoryRel> list);
}
