package com.fastproject.usergrowth.mapper;

import com.fastproject.usergrowth.domain.UserIntegralRecord;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordCreate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordUpdate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserIntegralRecordMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserIntegralRecordUpdate dto, @MappingTarget UserIntegralRecord entity);

    UserIntegralRecord toEntity(UserIntegralRecordCreate create);

    List<UserIntegralRecordVo> toVo(List<UserIntegralRecord> content);

    UserIntegralRecordVo toVo(UserIntegralRecord entity);
}
