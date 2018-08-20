package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.IndexArticle;
import com.test.entity.IndexArticleExample;
import com.test.mapper.IndexArticleMapper;
import com.test.service.IIndexArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexArticleServiceImpl implements IIndexArticleService {

    @Autowired
    private IndexArticleMapper indexArticleMapper;

    @Override
    public int add(IndexArticle obj) {
        if (StringUtils.isNotBlank(obj.getIaid())) {
            return indexArticleMapper.updateByPrimaryKey(obj);
        }
        return indexArticleMapper.insert(obj);
    }

    @Override
    public PageInfo<IndexArticle> findPage(int pageNo, int pageSize, IndexArticle obj) {
        PageHelper.startPage(pageNo, pageSize);
        IndexArticleExample example = new IndexArticleExample();
        if (obj.getStatus() != null) {
            example.createCriteria().andStatusEqualTo(obj.getStatus());
        }
        example.setOrderByClause("orderby desc");
        List<IndexArticle> iaList = indexArticleMapper.selectByExample(example);
        return new PageInfo<>(iaList);
    }

    @Override
    public IndexArticle selectByPrimaryKey(String sid) {
        return indexArticleMapper.selectByPrimaryKey(sid);
    }
}
