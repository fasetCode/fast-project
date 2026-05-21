package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysPermissions;
import com.fastproject.system.vo.permissions.SysPermissionsUpdate;
import com.fastproject.system.vo.permissions.SysPermissionsCreate;
import com.fastproject.system.vo.permissions.SysPermissionsVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysPermissionsMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermissionsFromDto(SysPermissionsUpdate dto, @MappingTarget SysPermissions entity);

    SysPermissions toPermissions(SysPermissionsCreate create);

    List<SysPermissionsVo> toVo(List<SysPermissions> content);

    SysPermissionsVo toVo(SysPermissions entity);
}
