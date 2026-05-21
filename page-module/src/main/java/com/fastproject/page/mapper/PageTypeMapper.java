package com.fastproject.page.mapper;

import com.fastproject.page.domain.PageType;
import com.fastproject.page.vo.pagetype.PageTypeUpdate;
import com.fastproject.page.vo.pagetype.PageTypeCreate;
import com.fastproject.page.vo.pagetype.PageTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface PageTypeMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePageTypeFromDto(PageTypeUpdate dto, @MappingTarget PageType entity);

    PageType toPageType(PageTypeCreate create);

    List<PageTypeVo> toVo(List<PageType> content);

    PageTypeVo toVo(PageType entity);
}
