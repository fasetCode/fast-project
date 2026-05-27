package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentTagRel;
import com.fastproject.content.vo.tagrel.ContentTagRelCreate;
import com.fastproject.content.vo.tagrel.ContentTagRelUpdate;
import com.fastproject.content.vo.tagrel.ContentTagRelVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentTagRelMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentTagRelUpdate dto, @MappingTarget ContentTagRel entity);

    ContentTagRel toEntity(ContentTagRelCreate create);

    ContentTagRelVo toVo(ContentTagRel entity);

    List<ContentTagRelVo> toVo(List<ContentTagRel> list);
}
