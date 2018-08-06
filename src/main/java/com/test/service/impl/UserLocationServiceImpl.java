package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.UserLocation;
import com.test.entity.UserLocationExample;
import com.test.mapper.UserLocationMapper;
import com.test.service.IUserLocationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserLocationServiceImpl implements IUserLocationService {

    @Autowired
    private UserLocationMapper userLocationMapper;

    @Override
    public int add(UserLocation obj) {
        if (StringUtils.isNotBlank(obj.getUlid())) {
            return userLocationMapper.updateByPrimaryKey(obj);
        }
        return userLocationMapper.insert(obj);
    }

    @Override
    public PageInfo<UserLocation> findPage(int pageNo, int pageSize, UserLocation obj) {
        PageHelper.startPage(pageNo,pageSize);
        UserLocationExample example = new UserLocationExample();
        example.setOrderByClause(" ulid desc");
        List<UserLocation> list = userLocationMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public UserLocation selectByPrimaryKey(String sid) {
        return userLocationMapper.selectByPrimaryKey(sid);
    }
}
