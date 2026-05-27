package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentCommentLike;
import com.fastproject.content.vo.commentlike.ContentCommentLikeCreate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeUpdate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentCommentLikeMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentCommentLikeUpdate dto, @MappingTarget ContentCommentLike entity);

    ContentCommentLike toEntity(ContentCommentLikeCreate create);

    ContentCommentLikeVo toVo(ContentCommentLike entity);

    List<ContentCommentLikeVo> toVo(List<ContentCommentLike> list);
}
