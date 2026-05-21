package com.fastproject.usergrowth.mapper;

import com.fastproject.usergrowth.domain.UserLevelAccount;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountCreate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountUpdate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserLevelAccountMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserLevelAccountUpdate dto, @MappingTarget UserLevelAccount entity);

    UserLevelAccount toEntity(UserLevelAccountCreate create);

    List<UserLevelAccountVo> toVo(List<UserLevelAccount> content);

    UserLevelAccountVo toVo(UserLevelAccount entity);
}
