package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentComment;
import com.fastproject.content.vo.comment.ContentCommentCreate;
import com.fastproject.content.vo.comment.ContentCommentUpdate;
import com.fastproject.content.vo.comment.ContentCommentVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentCommentMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentCommentUpdate dto, @MappingTarget ContentComment entity);

    ContentComment toEntity(ContentCommentCreate create);

    ContentCommentVo toVo(ContentComment entity);

    List<ContentCommentVo> toVo(List<ContentComment> list);
}
