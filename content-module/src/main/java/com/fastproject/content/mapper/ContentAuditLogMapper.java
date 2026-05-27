package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentAuditLog;
import com.fastproject.content.vo.auditlog.ContentAuditLogCreate;
import com.fastproject.content.vo.auditlog.ContentAuditLogUpdate;
import com.fastproject.content.vo.auditlog.ContentAuditLogVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentAuditLogMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentAuditLogUpdate dto, @MappingTarget ContentAuditLog entity);

    ContentAuditLog toEntity(ContentAuditLogCreate create);

    ContentAuditLogVo toVo(ContentAuditLog entity);

    List<ContentAuditLogVo> toVo(List<ContentAuditLog> list);
}
