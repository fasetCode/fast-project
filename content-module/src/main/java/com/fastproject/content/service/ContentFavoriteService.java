package com.fastproject.content.service;

import com.fastproject.content.vo.favorite.ContentFavoriteCreate;
import com.fastproject.content.vo.favorite.ContentFavoriteQuery;
import com.fastproject.content.vo.favorite.ContentFavoriteUpdate;
import com.fastproject.content.vo.favorite.ContentFavoriteVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface ContentFavoriteService {
    Long save(ContentFavoriteCreate create);

    void update(ContentFavoriteUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    ContentFavoriteVo findById(Long id);

    PageVo<List<ContentFavoriteVo>> findPage(ContentFavoriteQuery query);

    List<ContentFavoriteVo> findAll();

    List<ContentFavoriteVo> selectAll();
}
