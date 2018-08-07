package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.Viptxt;
import com.test.entity.ViptxtExample;
import com.test.mapper.ViptxtMapper;
import com.test.service.IVipTxtSerivce;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VipTxtServiceImpl implements IVipTxtSerivce {

    @Autowired
    private ViptxtMapper viptxtMapper;

    @Override
    public int add(Viptxt obj) {
        if (StringUtils.isNotBlank(obj.getVtid())) {
            return viptxtMapper.updateByPrimaryKey(obj);
        }
        return viptxtMapper.insert(obj);
    }

    @Override
    public PageInfo<Viptxt> findPage(int pageNo, int pageSize, Viptxt obj) {
        PageHelper.startPage(pageNo,pageSize);
        ViptxtExample example = new ViptxtExample();
        example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(obj.getTitle())) {
            example.createCriteria().andTitleLike("%"+obj.getTitle()+"%");
        }
        List list = viptxtMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Viptxt selectByPrimaryKey(String sid) {
        return selectByPrimaryKey(sid);
    }
}
