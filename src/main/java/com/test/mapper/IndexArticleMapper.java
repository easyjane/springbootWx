package com.test.mapper;

import com.test.entity.IndexArticle;
import com.test.entity.IndexArticleExample;
import java.util.List;

public interface IndexArticleMapper {
    int countByExample(IndexArticleExample example);

    int deleteByPrimaryKey(String iaid);

    int insert(IndexArticle record);

    int insertSelective(IndexArticle record);

    List<IndexArticle> selectByExample(IndexArticleExample example);

    IndexArticle selectByPrimaryKey(String iaid);

    int updateByPrimaryKeySelective(IndexArticle record);

    int updateByPrimaryKey(IndexArticle record);
}