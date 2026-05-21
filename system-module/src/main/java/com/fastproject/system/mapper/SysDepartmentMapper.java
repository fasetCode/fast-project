package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysDepartment;
import com.fastproject.system.vo.department.SysDepartmentUpdate;
import com.fastproject.system.vo.department.SysDepartmentCreate;
import com.fastproject.system.vo.department.SysDepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysDepartmentMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDepartmentFromDto(SysDepartmentUpdate dto, @MappingTarget SysDepartment entity);

    SysDepartment toDepartment(SysDepartmentCreate create);

    List<SysDepartmentVo> toVo(List<SysDepartment> content);

    SysDepartmentVo toVo(SysDepartment entity);
}
