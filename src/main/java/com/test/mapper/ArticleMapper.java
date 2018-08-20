package com.test.mapper;

import com.test.entity.Article;
import com.test.entity.ArticleExample;
import java.util.List;

public interface ArticleMapper {
    int countByExample(ArticleExample example);

    int deleteByPrimaryKey(String aid);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(String aid);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
}