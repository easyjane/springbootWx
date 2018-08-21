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
        PageHelper.startPage(pageNo,pageSize);
        UserSignExample example = new UserSignExample();

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
        return userSignMapper.login(new UserSign(username,phone));
    }
}
