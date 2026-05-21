package com.fastproject.ratelimit.mapper;

import com.fastproject.ratelimit.domain.RateLimitRecord;
import com.fastproject.ratelimit.vo.record.RateLimitRecordCreate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordUpdate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface RateLimitRecordMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecordFromDto(RateLimitRecordUpdate dto, @MappingTarget RateLimitRecord entity);

    RateLimitRecord toRecord(RateLimitRecordCreate create);

    List<RateLimitRecordVo> toVo(List<RateLimitRecord> content);

    RateLimitRecordVo toVo(RateLimitRecord entity);
}