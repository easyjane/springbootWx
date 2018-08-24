package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.UserSign;
import com.test.entity.UserSignExample;
import com.test.mapper.UserSignMapper;
import com.test.service.IUserSignService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSignServiceImpl implements IUserSignService {

    @Autowired
    private UserSignMapper userSignMapper;

    @Override
    public int add(UserSign obj) {
        if (StringUtils.isNotBlank(obj.getUsid())) {
            return userSignMapper.updateByPrimaryKey(obj);
        }
        return userSignMapper.insert(obj);
    }

    @Override
    public PageInfo<UserSign> findPage(int pageNo, int pageSize, UserSign obj) {
        PageHelper.startPage(pageNo, pageSize);
        UserSignExample example = new UserSignExample();

        UserSignExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(obj.getTableName())) {
            criteria.andTableNameEqualTo(obj.getTableName());
        }
        example.setOrderByClause("create_time desc");
        List list = userSignMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<UserSign> findPageLikeItemOr(int pageNo, int pageSize, UserSign obj) {
        PageHelper.startPage(pageNo, pageSize);
        UserSignExample example = new UserSignExample();

        if (obj.getIsSign() != null) {

            if (StringUtils.isNotBlank(obj.getTableName())) {
                UserSignExample.Criteria criteria1 = example.createCriteria();
                criteria1.andTableNameLike("%" + obj.getTableName() + "%");
                criteria1.andIsSignEqualTo(obj.getIsSign());
                example.or(criteria1);
            } else {
                UserSignExample.Criteria criteria = example.createCriteria();
                criteria.andIsSignEqualTo(obj.getIsSign());
            }
            if (StringUtils.isNotBlank(obj.getUsername())) {
                UserSignExample.Criteria criteria1 = example.createCriteria();
                criteria1.andUsernameLike("%" + obj.getUsername() + "%");
                criteria1.andIsSignEqualTo(obj.getIsSign());
                example.or(criteria1);
            }
            if (StringUtils.isNotBlank(obj.getPhone())) {
                UserSignExample.Criteria criteria1 = example.createCriteria();
                criteria1.andPhoneLike("%" + obj.getTableName() + "%");
                criteria1.andIsSignEqualTo(obj.getIsSign());
                example.or(criteria1);
            }

        } else {
            if (StringUtils.isNotBlank(obj.getTableName())) {
                UserSignExample.Criteria criteria1 = example.createCriteria();
                criteria1.andTableNameLike("%" + obj.getTableName() + "%");
                example.or(criteria1);
            }
            if (StringUtils.isNotBlank(obj.getUsername())) {
                UserSignExample.Criteria criteria1 = example.createCriteria();
                criteria1.andUsernameLike("%" + obj.getUsername() + "%");
                example.or(criteria1);
            }
            if (StringUtils.isNotBlank(obj.getPhone())) {
                UserSignExample.Criteria criteria1 = example.createCriteria();
                criteria1.andPhoneLike("%" + obj.getTableName() + "%");
                example.or(criteria1);
            }
        }

        example.setOrderByClause("create_time desc");
        List list = userSignMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public UserSign selectByPrimaryKey(String sid) {
        return userSignMapper.selectByPrimaryKey(sid);
    }

    @Override
    public UserSign login(String username, String phone) {
        return userSignMapper.login(new UserSign(username, phone));
    }
}
