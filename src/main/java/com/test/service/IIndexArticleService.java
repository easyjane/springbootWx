package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.IndexArticle;

public interface IIndexArticleService extends IBaseService<IndexArticle,String> {

    @Override
    int add(IndexArticle obj);

    @Override
    PageInfo<IndexArticle> findPage(int pageNo, int pageSize, IndexArticle obj);

    @Override
    IndexArticle selectByPrimaryKey(String sid);
}
