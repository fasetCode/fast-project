package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentTag;
import com.fastproject.content.vo.tag.ContentTagCreate;
import com.fastproject.content.vo.tag.ContentTagUpdate;
import com.fastproject.content.vo.tag.ContentTagVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentTagMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentTagUpdate dto, @MappingTarget ContentTag entity);

    ContentTag toEntity(ContentTagCreate create);

    ContentTagVo toVo(ContentTag entity);

    List<ContentTagVo> toVo(List<ContentTag> list);
}
