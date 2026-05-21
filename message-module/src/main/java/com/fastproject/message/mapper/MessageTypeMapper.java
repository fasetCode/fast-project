package com.fastproject.message.mapper;

import com.fastproject.message.domain.MessageType;
import com.fastproject.message.vo.type.MessageTypeUpdate;
import com.fastproject.message.vo.type.MessageTypeCreate;
import com.fastproject.message.vo.type.MessageTypeVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface MessageTypeMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTypeFromDto(MessageTypeUpdate dto, @MappingTarget MessageType entity);

    MessageType toType(MessageTypeCreate create);

    List<MessageTypeVo> toVo(List<MessageType> content);

    MessageTypeVo toVo(MessageType entity);
}