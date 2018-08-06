package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.UserFocus;
import com.test.entity.UserFocusExample;
import com.test.mapper.UserFocusMapper;
import com.test.service.IUserFocusService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserFocusServiceImpl implements IUserFocusService {

    @Autowired
    private UserFocusMapper userFocusMapper;

    @Override
    public int add(UserFocus obj) {
        if (StringUtils.isNotBlank(obj.getUfid())) {
            return userFocusMapper.updateByPrimaryKey(obj);
        }
        return userFocusMapper.insert(obj);
    }

    @Override
    public PageInfo<UserFocus> findPage(int pageNo, int pageSize, UserFocus obj) {
        PageHelper.startPage(pageNo,pageSize);
        UserFocusExample example = new UserFocusExample();
        example.setOrderByClause("ufid desc");
        List<UserFocus> list = userFocusMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public UserFocus selectByPrimaryKey(String sid) {
        return userFocusMapper.selectByPrimaryKey(sid);
    }
}
