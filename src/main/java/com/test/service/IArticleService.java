package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.Article;

public interface IArticleService extends IBaseService<Article,String> {

    @Override
    int add(Article obj);

    @Override
    PageInfo<Article> findPage(int pageNo, int pageSize, Article obj);

    @Override
    Article selectByPrimaryKey(String sid);
}
