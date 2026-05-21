package com.fastproject.ratelimit.mapper;

import com.fastproject.ratelimit.domain.IpRateLimitConfig;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface IpRateLimitConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(IpRateLimitConfigUpdate dto, @MappingTarget IpRateLimitConfig entity);

    IpRateLimitConfig toConfig(IpRateLimitConfigCreate create);

    List<IpRateLimitConfigVo> toVo(List<IpRateLimitConfig> content);

    IpRateLimitConfigVo toVo(IpRateLimitConfig entity);
}