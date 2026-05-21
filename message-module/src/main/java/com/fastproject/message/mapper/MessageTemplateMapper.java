package com.fastproject.message.mapper;

import com.fastproject.message.domain.MessageTemplate;
import com.fastproject.message.vo.template.MessageTemplateUpdate;
import com.fastproject.message.vo.template.MessageTemplateCreate;
import com.fastproject.message.vo.template.MessageTemplateVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface MessageTemplateMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTemplateFromDto(MessageTemplateUpdate dto, @MappingTarget MessageTemplate entity);

    MessageTemplate toTemplate(MessageTemplateCreate create);

    List<MessageTemplateVo> toVo(List<MessageTemplate> content);

    MessageTemplateVo toVo(MessageTemplate entity);
}