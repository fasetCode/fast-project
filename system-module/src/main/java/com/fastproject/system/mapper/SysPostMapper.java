package com.fastproject.system.mapper;

import com.fastproject.system.domain.SysPost;
import com.fastproject.system.vo.post.SysPostUpdate;
import com.fastproject.system.vo.post.SysPostCreate;
import com.fastproject.system.vo.post.SysPostVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface SysPostMapper {

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(SysPostUpdate dto, @MappingTarget SysPost entity);

    SysPost toPost(SysPostCreate create);

    List<SysPostVo> toVo(List<SysPost> content);

    SysPostVo toVo(SysPost entity);
}
