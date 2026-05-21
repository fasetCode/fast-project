package com.fastproject.file.mapper;

import com.fastproject.file.domain.FileConfig;
import com.fastproject.file.vo.config.FileConfigCreate;
import com.fastproject.file.vo.config.FileConfigUpdate;
import com.fastproject.file.vo.config.FileConfigVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileConfigMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(FileConfigUpdate dto, @MappingTarget FileConfig entity);

    FileConfig toConfig(FileConfigCreate create);

    List<FileConfigVo> toVo(List<FileConfig> content);

    FileConfigVo toVo(FileConfig entity);
}
