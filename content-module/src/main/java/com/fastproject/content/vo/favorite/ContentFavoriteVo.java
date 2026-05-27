package com.fastproject.content.vo.favorite;

import lombok.Data;

@Data
public class ContentFavoriteVo {
    private Long id;
    private Long contentId;
    private Long userId;
}
