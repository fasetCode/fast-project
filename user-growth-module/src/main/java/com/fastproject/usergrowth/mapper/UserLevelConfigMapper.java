package com.fastproject.usergrowth.mapper;

import com.fastproject.usergrowth.domain.UserLevelConfig;
import com.fastproject.usergrowth.vo.levelconfig.UserLevelConfigCreate;
import com.fastproject.usergrowth.vo.levelconfig.UserLevelConfigUpdate;
import com.fastproject.usergrowth.vo.levelconfig.UserLevelConfigVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface UserLevelConfigMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserLevelConfigUpdate dto, @MappingTarget UserLevelConfig entity);

    UserLevelConfig toEntity(UserLevelConfigCreate create);

    List<UserLevelConfigVo> toVo(List<UserLevelConfig> content);

    UserLevelConfigVo toVo(UserLevelConfig entity);
}
