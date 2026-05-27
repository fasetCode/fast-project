package com.fastproject.content.mapper;

import com.fastproject.content.domain.ContentFavorite;
import com.fastproject.content.vo.favorite.ContentFavoriteCreate;
import com.fastproject.content.vo.favorite.ContentFavoriteUpdate;
import com.fastproject.content.vo.favorite.ContentFavoriteVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ContentFavoriteMapper {
    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ContentFavoriteUpdate dto, @MappingTarget ContentFavorite entity);

    ContentFavorite toEntity(ContentFavoriteCreate create);

    ContentFavoriteVo toVo(ContentFavorite entity);

    List<ContentFavoriteVo> toVo(List<ContentFavorite> list);
}
