package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysConfig;
import com.fastproject.system.vo.config.SysConfigUpdate;
import com.fastproject.system.vo.config.SysConfigCreate;
import com.fastproject.system.vo.config.SysConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(SysConfigUpdate dto, @MappingTarget SysConfig entity);

    SysConfig toConfig(SysConfigCreate create);

    List<SysConfigVo> toVo(List<SysConfig> content);

    SysConfigVo toVo(SysConfig entity);
}
