package com.fastproject.ratelimit.mapper;

import com.fastproject.ratelimit.domain.IpBlackWhiteList;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface IpBlackWhiteListMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(IpBlackWhiteListUpdate dto, @MappingTarget IpBlackWhiteList entity);

    IpBlackWhiteList toConfig(IpBlackWhiteListCreate create);

    List<IpBlackWhiteListVo> toVo(List<IpBlackWhiteList> content);

    IpBlackWhiteListVo toVo(IpBlackWhiteList entity);
}
