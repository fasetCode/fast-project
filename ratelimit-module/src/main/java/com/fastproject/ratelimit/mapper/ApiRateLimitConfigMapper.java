package com.fastproject.ratelimit.mapper;

import com.fastproject.ratelimit.domain.ApiRateLimitConfig;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface ApiRateLimitConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateConfigFromDto(ApiRateLimitConfigUpdate dto, @MappingTarget ApiRateLimitConfig entity);

    ApiRateLimitConfig toConfig(ApiRateLimitConfigCreate create);

    List<ApiRateLimitConfigVo> toVo(List<ApiRateLimitConfig> content);

    ApiRateLimitConfigVo toVo(ApiRateLimitConfig entity);
}