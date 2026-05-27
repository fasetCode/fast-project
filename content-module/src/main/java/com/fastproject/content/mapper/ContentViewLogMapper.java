package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentViewLog;
import com.fastproject.content.vo.viewlog.ContentViewLogCreate;
import com.fastproject.content.vo.viewlog.ContentViewLogUpdate;
import com.fastproject.content.vo.viewlog.ContentViewLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentViewLogMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentViewLogUpdate dto, @MappingTarget ContentViewLog entity);

    ContentViewLog toEntity(ContentViewLogCreate create);

    ContentViewLogVo toVo(ContentViewLog entity);

    List<ContentViewLogVo> toVo(List<ContentViewLog> list);
}
