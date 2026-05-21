package com.fastproject.usergrowth.mapper;

import com.fastproject.usergrowth.domain.UserIntegralAccount;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountCreate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountUpdate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserIntegralAccountMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserIntegralAccountUpdate dto, @MappingTarget UserIntegralAccount entity);

    UserIntegralAccount toEntity(UserIntegralAccountCreate create);

    List<UserIntegralAccountVo> toVo(List<UserIntegralAccount> content);

    UserIntegralAccountVo toVo(UserIntegralAccount entity);
}
