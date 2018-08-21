package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.UserSign;

public interface IUserSignService extends IBaseService<UserSign,String> {

    @Override
    int add(UserSign obj);

    @Override
    PageInfo<UserSign> findPage(int pageNo, int pageSize, UserSign obj);

    PageInfo<UserSign> findPageLikeItemOr(int pageNo, int pageSize, UserSign obj);

    @Override
    UserSign selectByPrimaryKey(String sid);

    UserSign login(String username,String phone);
}
