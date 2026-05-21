package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysUsers;
import com.fastproject.system.vo.users.SysUserUpdate;
import com.fastproject.system.vo.users.SysUsersCreate;
import com.fastproject.vo.SysUsersLoginVo;
import com.fastproject.system.vo.users.SysUsersVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysUsersMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(SysUserUpdate dto, @MappingTarget SysUsers entity);

    SysUsers toUser(SysUsersCreate create);

    List<SysUsersVo> toVo(List<SysUsers> content);

    SysUsersVo toVo(SysUsers entity);

    SysUsersLoginVo toLoginUser(SysUsers byUsername);
}