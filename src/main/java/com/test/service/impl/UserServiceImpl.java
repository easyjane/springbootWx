package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.User;
import com.test.entity.UserExample;
import com.test.mapper.UserMapper;
import com.test.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int add(User obj) {
        if (StringUtils.isNotBlank(obj.getUid())) {
            return userMapper.updateByPrimaryKey(obj);
        } else
        return userMapper.insert(obj);
    }

    @Override
    public PageInfo<User> findPage(int pageNo, int pageSize, User obj) {
        PageHelper.startPage(pageNo,pageSize);
        UserExample example = new UserExample();
        example.setOrderByClause("uid desc");

        List<User> list = userMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public User selectByPrimaryKey(String sid) {
        return userMapper.selectByPrimaryKey(sid);
    }

    @Override
    public User findByOpenId(String openid) {
        UserExample example = new UserExample();
        example.createCriteria().andOpenIdEqualTo(openid);
        List<User> list = userMapper.selectByExample(example);
        return list.size()>0 ? list.get(0) : new User();
    }
}
