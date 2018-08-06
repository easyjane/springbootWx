package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.test.entity.Supermarket;
import com.test.entity.SupermarketExample;
import com.test.mapper.SupermarketMapper;
import com.test.service.ISuperMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SuperMarketServiceImpl implements ISuperMarketService {

    @Autowired
    private SupermarketMapper supermarketMapper;
    @Override
    public int addSuperMarket(Supermarket smk) {

        int count = supermarketMapper.insertSelective(smk);
        return count;
    }

    @Override
    public PageInfo<Supermarket> findPage(int pageNo, int pageSize,Supermarket smk) {
        PageHelper.startPage(pageNo,pageSize);
        SupermarketExample example = new SupermarketExample();
        if (StringUtil.isNotEmpty(smk.getName())) {
            example.createCriteria().andNameEqualTo(smk.getName());
        }
        if (StringUtil.isNotEmpty(smk.getUserpwd())) {
            example.createCriteria().andUserpwdEqualTo(smk.getUserpwd());
        }
        example.setOrderByClause("sid desc");
        List<Supermarket> smkList = supermarketMapper.selectByExample(example);
        return new PageInfo<>(smkList);
    }

    @Override
    public Supermarket selectByPrimaryKey(String sid) {
        return supermarketMapper.selectByPrimaryKey(sid);
    }
}
