package com.fastproject.ratelimit.mapper;

import com.fastproject.ratelimit.domain.UserBlackWhiteList;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserBlackWhiteListMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(UserBlackWhiteListUpdate dto, @MappingTarget UserBlackWhiteList entity);

    UserBlackWhiteList toConfig(UserBlackWhiteListCreate create);

    List<UserBlackWhiteListVo> toVo(List<UserBlackWhiteList> content);

    UserBlackWhiteListVo toVo(UserBlackWhiteList entity);
}
