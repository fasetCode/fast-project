package com.fastproject.message.mapper;

import com.fastproject.message.domain.MessageConfig;
import com.fastproject.message.vo.config.MessageConfigUpdate;
import com.fastproject.message.vo.config.MessageConfigCreate;
import com.fastproject.message.vo.config.MessageConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface MessageConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(MessageConfigUpdate dto, @MappingTarget MessageConfig entity);

    MessageConfig toConfig(MessageConfigCreate create);

    List<MessageConfigVo> toVo(List<MessageConfig> content);

    MessageConfigVo toVo(MessageConfig entity);
}