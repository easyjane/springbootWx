package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.User;

public interface IUserService extends IBaseService<User,String> {


    @Override
    int add(User obj);

    @Override
    PageInfo<User> findPage(int pageNo, int pageSize, User obj);

    @Override
    User selectByPrimaryKey(String sid);

    User findByOpenId(String openid);
}
