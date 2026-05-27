package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentInfo;
import com.fastproject.content.vo.info.ContentInfoCreate;
import com.fastproject.content.vo.info.ContentInfoUpdate;
import com.fastproject.content.vo.info.ContentInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentInfoMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentInfoUpdate dto, @MappingTarget ContentInfo entity);

    ContentInfo toEntity(ContentInfoCreate create);

    ContentInfoVo toVo(ContentInfo entity);

    List<ContentInfoVo> toVo(List<ContentInfo> list);
}
