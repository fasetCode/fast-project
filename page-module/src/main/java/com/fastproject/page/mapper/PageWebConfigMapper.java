package com.fastproject.page.mapper;

import com.fastproject.page.domain.PageWebConfig;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigUpdate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigCreate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PageWebConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePageWebConfigFromDto(PageWebConfigUpdate dto, @MappingTarget PageWebConfig entity);

    PageWebConfig toPageWebConfig(PageWebConfigCreate create);

    List<PageWebConfigVo> toVo(List<PageWebConfig> content);

    PageWebConfigVo toVo(PageWebConfig entity);
}
