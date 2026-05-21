package com.fastproject.file.mapper;

import com.fastproject.file.domain.FileDomain;
import com.fastproject.file.vo.domain.FileDomainCreate;
import com.fastproject.file.vo.domain.FileDomainUpdate;
import com.fastproject.file.vo.domain.FileDomainVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * 文件域名 Mapper
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FileDomainMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDomainFromDto(FileDomainUpdate dto, @MappingTarget FileDomain entity);

    FileDomain toDomain(FileDomainCreate create);

    List<FileDomainVo> toVo(List<FileDomain> content);

    FileDomainVo toVo(FileDomain entity);
}
