package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentReport;
import com.fastproject.content.vo.report.ContentReportCreate;
import com.fastproject.content.vo.report.ContentReportUpdate;
import com.fastproject.content.vo.report.ContentReportVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentReportMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentReportUpdate dto, @MappingTarget ContentReport entity);

    ContentReport toEntity(ContentReportCreate create);

    ContentReportVo toVo(ContentReport entity);

    List<ContentReportVo> toVo(List<ContentReport> list);
}
