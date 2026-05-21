package com.fastproject.usergrowth.mapper;

import com.fastproject.usergrowth.domain.UserLevelRecord;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordCreate;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordUpdate;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserLevelRecordMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserLevelRecordUpdate dto, @MappingTarget UserLevelRecord entity);

    UserLevelRecord toEntity(UserLevelRecordCreate create);

    List<UserLevelRecordVo> toVo(List<UserLevelRecord> content);

    UserLevelRecordVo toVo(UserLevelRecord entity);
}
