package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentBody;
import com.fastproject.content.vo.body.ContentBodyCreate;
import com.fastproject.content.vo.body.ContentBodyUpdate;
import com.fastproject.content.vo.body.ContentBodyVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentBodyMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentBodyUpdate dto, @MappingTarget ContentBody entity);

    ContentBody toEntity(ContentBodyCreate create);

    ContentBodyVo toVo(ContentBody entity);

    List<ContentBodyVo> toVo(List<ContentBody> list);
}
