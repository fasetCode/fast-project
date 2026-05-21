package com.fastproject.ratelimit.mapper;

import com.fastproject.ratelimit.domain.GlobalRateLimitConfig;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface GlobalRateLimitConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(GlobalRateLimitConfigUpdate dto, @MappingTarget GlobalRateLimitConfig entity);

    GlobalRateLimitConfig toConfig(GlobalRateLimitConfigCreate create);

    List<GlobalRateLimitConfigVo> toVo(List<GlobalRateLimitConfig> content);

    GlobalRateLimitConfigVo toVo(GlobalRateLimitConfig entity);
}