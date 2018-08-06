package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.Article;
import com.test.entity.ArticleExample;
import com.test.mapper.ArticleMapper;
import com.test.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements IArticleService {


    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public int add(Article obj) {
        Article article = new Article();
        article.setContent("这里是内容");
        article.setCreateTime(new Date());
        article.setDate("2010-01-02");
        return articleMapper.insert(article);
    }

    @Override
    public PageInfo<Article> findPage(int pageNo, int pageSize, Article obj) {
        PageHelper.startPage(pageNo,pageSize);
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("aid desc");
        List<Article> list = articleMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Article selectByPrimaryKey(String sid) {
        return articleMapper.selectByPrimaryKey(sid);
    }
}
