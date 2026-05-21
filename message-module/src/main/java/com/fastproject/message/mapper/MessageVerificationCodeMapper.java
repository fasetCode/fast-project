package com.fastproject.message.mapper;

import com.fastproject.message.domain.MessageVerificationCode;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeUpdate;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeCreate;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface MessageVerificationCodeMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVerificationCodeFromDto(MessageVerificationCodeUpdate dto, @MappingTarget MessageVerificationCode entity);

    MessageVerificationCode toVerificationCode(MessageVerificationCodeCreate create);

    List<MessageVerificationCodeVo> toVo(List<MessageVerificationCode> content);

    MessageVerificationCodeVo toVo(MessageVerificationCode entity);
}