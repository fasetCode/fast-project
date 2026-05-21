package com.fastproject.page.mapper;

import com.fastproject.page.domain.PageApplication;
import com.fastproject.page.vo.pageapplication.PageApplicationUpdate;
import com.fastproject.page.vo.pageapplication.PageApplicationCreate;
import com.fastproject.page.vo.pageapplication.PageApplicationVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PageApplicationMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePageApplicationFromDto(PageApplicationUpdate dto, @MappingTarget PageApplication entity);

    PageApplication toPageApplication(PageApplicationCreate create);

    List<PageApplicationVo> toVo(List<PageApplication> content);

    PageApplicationVo toVo(PageApplication entity);
}
