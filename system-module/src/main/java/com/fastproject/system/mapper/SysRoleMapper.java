package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysRole;
import com.fastproject.system.vo.role.SysRoleUpdate;
import com.fastproject.system.vo.role.SysRoleCreate;
import com.fastproject.system.vo.role.SysRoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysRoleMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromDto(SysRoleUpdate dto, @MappingTarget SysRole entity);

    @Mapping(target = "permissions", ignore = true)
    SysRole toRole(SysRoleCreate create);

    List<SysRoleVo> toVo(List<SysRole> content);

    @Mapping(target = "permissionIds", ignore = true)
    SysRoleVo toVo(SysRole entity);
}
