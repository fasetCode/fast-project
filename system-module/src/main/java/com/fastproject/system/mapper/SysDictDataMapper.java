package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysDictData;
import com.fastproject.system.vo.dictdata.SysDictDataUpdate;
import com.fastproject.system.vo.dictdata.SysDictDataCreate;
import com.fastproject.system.vo.dictdata.SysDictDataVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysDictDataMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDictDataFromDto(SysDictDataUpdate dto, @MappingTarget SysDictData entity);

    SysDictData toDictData(SysDictDataCreate create);

    List<SysDictDataVo> toVo(List<SysDictData> content);

    SysDictDataVo toVo(SysDictData entity);
}
