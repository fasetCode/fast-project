package com.fastproject.page.mapper;

import com.fastproject.page.domain.PageComponent;
import com.fastproject.page.vo.pagecomponent.PageComponentUpdate;
import com.fastproject.page.vo.pagecomponent.PageComponentCreate;
import com.fastproject.page.vo.pagecomponent.PageComponentVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PageComponentMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePageComponentFromDto(PageComponentUpdate dto, @MappingTarget PageComponent entity);

    PageComponent toPageComponent(PageComponentCreate create);

    List<PageComponentVo> toVo(List<PageComponent> content);

    PageComponentVo toVo(PageComponent entity);
}
