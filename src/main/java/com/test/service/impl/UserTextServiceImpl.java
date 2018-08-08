package com.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.entity.UserText;
import com.test.entity.UserTextExample;
import com.test.mapper.UserTextMapper;
import com.test.service.IUserTextService;
import com.test.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserTextServiceImpl implements IUserTextService {

    @Autowired
    private UserTextMapper userTextMapper;

    @Override
    public int add(UserText obj) {
        if (StringUtils.isNotBlank(obj.getUtid()))
            return userTextMapper.updateByPrimaryKey(obj);
        return userTextMapper.insert(obj);
    }

    @Override
    public PageInfo<UserText> findPage(int pageNo, int pageSize, UserText obj) {
        PageHelper.startPage(pageNo,pageSize);
        UserTextExample example = new UserTextExample();
        UserTextExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(obj.getOpenId())) {
            criteria.andOpenIdEqualTo(obj.getOpenId());
        }
        if (obj.getCreateTime() != null) {
            Date now = new Date();
            criteria.andCreateTimeLessThan(DateUtil.addMinute(now,30));
        }
        if (obj.getStatus() != null) {
            criteria.andStatusEqualTo(obj.getStatus());
        }

        example.setOrderByClause("create_time desc");
        List<UserText> list = userTextMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public UserText selectByPrimaryKey(String sid) {
        return userTextMapper.selectByPrimaryKey(sid);
    }
}
