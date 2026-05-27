package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentAttachment;
import com.fastproject.content.vo.attachment.ContentAttachmentCreate;
import com.fastproject.content.vo.attachment.ContentAttachmentUpdate;
import com.fastproject.content.vo.attachment.ContentAttachmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentAttachmentMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentAttachmentUpdate dto, @MappingTarget ContentAttachment entity);

    ContentAttachment toEntity(ContentAttachmentCreate create);

    ContentAttachmentVo toVo(ContentAttachment entity);

    List<ContentAttachmentVo> toVo(List<ContentAttachment> list);
}
