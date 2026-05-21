package com.fastproject.page.mapper;

import com.fastproject.page.domain.PageConfig;
import com.fastproject.page.vo.pageconfig.PageConfigListVo;
import com.fastproject.page.vo.pageconfig.PageConfigUpdate;
import com.fastproject.page.vo.pageconfig.PageConfigCreate;
import com.fastproject.page.vo.pageconfig.PageConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PageConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePageConfigFromDto(PageConfigUpdate dto, @MappingTarget PageConfig entity);

    PageConfig toPageConfig(PageConfigCreate create);

    List<PageConfigVo> toVo(List<PageConfig> content);

    PageConfigVo toVo(PageConfig entity);

    List<PageConfigListVo> toListVo(List<PageConfig> list);
}
