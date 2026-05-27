package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentRevision;
import com.fastproject.content.vo.revision.ContentRevisionCreate;
import com.fastproject.content.vo.revision.ContentRevisionUpdate;
import com.fastproject.content.vo.revision.ContentRevisionVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentRevisionMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentRevisionUpdate dto, @MappingTarget ContentRevision entity);

    ContentRevision toEntity(ContentRevisionCreate create);

    ContentRevisionVo toVo(ContentRevision entity);

    List<ContentRevisionVo> toVo(List<ContentRevision> list);
}
