package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentLike;
import com.fastproject.content.vo.like.ContentLikeCreate;
import com.fastproject.content.vo.like.ContentLikeUpdate;
import com.fastproject.content.vo.like.ContentLikeVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentLikeMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentLikeUpdate dto, @MappingTarget ContentLike entity);

    ContentLike toEntity(ContentLikeCreate create);

    ContentLikeVo toVo(ContentLike entity);

    List<ContentLikeVo> toVo(List<ContentLike> list);
}
