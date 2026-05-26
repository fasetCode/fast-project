package com.fastproject.content.service;

import com.fastproject.content.domain.ContentCategory;

import java.util.List;

public interface ContentCategoryService extends CrudService<ContentCategory> {
    List<ContentCategory> findTree();
}
