package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysDictType;
import com.fastproject.system.vo.dicttype.SysDictTypeUpdate;
import com.fastproject.system.vo.dicttype.SysDictTypeCreate;
import com.fastproject.system.vo.dicttype.SysDictTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysDictTypeMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDictTypeFromDto(SysDictTypeUpdate dto, @MappingTarget SysDictType entity);

    SysDictType toDictType(SysDictTypeCreate create);

    List<SysDictTypeVo> toVo(List<SysDictType> content);

    SysDictTypeVo toVo(SysDictType entity);
}
